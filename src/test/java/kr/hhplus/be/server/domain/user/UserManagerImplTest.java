package kr.hhplus.be.server.domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class UserManagerImplTest {

    private UserManagerImpl userManager;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        userManager = new UserManagerImpl(userRepository);
    }

    @Test
    void createUser_success() {
        // given
        String userId = "user123";
        String username = "홍길동";

        // when
        User created = userManager.create(userId, username);

        // then
        assertNotNull(created);
        assertEquals(userId, created.getUserId());
        assertEquals(username, created.getUsername());
        assertNotNull(created.getQueueToken());  // 기본 생성 시 빈 문자열일 수도
    }
}
