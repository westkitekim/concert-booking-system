package kr.hhplus.be.server.domain.queuetoken;

import kr.hhplus.be.server.domain.user.User;
import kr.hhplus.be.server.domain.user.UserManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QueueTokenServiceTest {

    @Mock
    private QueueTokenRepository queueTokenRepository;

    @Mock
    private TokenGenerator tokenGenerator;

    @Mock
    private UserManager userManager;

    @InjectMocks
    private QueueTokenService queueTokenService;

    @Test
    void issueToken_success() {
        // given
        User user = new User(1L, "testUser", "");
        String generatedToken = "generated-token-123";

        given(userManager.create(anyString(), anyString())).willReturn(user);
        given(tokenGenerator.generateToken(anyLong())).willReturn(generatedToken); // anyLong
        when(queueTokenRepository.save(any(QueueToken.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // when
        QueueToken token = queueTokenService.issueToken(user.getUsername());

        // then
        assertEquals(generatedToken, token.getToken());
        assertEquals(QueueTokenStatusEnum.PENDING, token.getTokenStatus());
        assertEquals(user.getUserId(), token.getUserId());
        verify(queueTokenRepository).save(token);
    }

    @Test
    void change_tokenStatus_success() {
        // given
        User user = new User(1L, "testUser", "");
        QueueToken token = new QueueToken(user.getUserId(), "token-123", 1);
        given(queueTokenRepository.findByToken("token-123")).willReturn(Optional.of(token));

        // when
        queueTokenService.changeStatus("token-123", QueueTokenStatusEnum.PENDING);

        // then
        assertEquals(QueueTokenStatusEnum.PENDING, token.getTokenStatus());
        verify(queueTokenRepository).save(token);
    }

    @Test
    void selectToken_success_by_Id() {
        // given
        User user = new User(4L, "testUser", "");
        QueueToken token = new QueueToken(3L, user.getUserId(), "token-456", 1);
        token.changeTokenStatus(QueueTokenStatusEnum.PENDING);

        given(queueTokenRepository.findByToken("token-456")).willReturn(Optional.of(token));

        // when
        QueueTokenStatusEnum status = queueTokenService.getStatus("token-456");

        // then
        assertEquals(QueueTokenStatusEnum.PENDING, status);
    }
}


