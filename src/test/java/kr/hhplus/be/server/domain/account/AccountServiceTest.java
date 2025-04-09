package kr.hhplus.be.server.domain.account;

import kr.hhplus.be.server.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    AccountRepository repository; // Repository mock 처리

    @InjectMocks
    AccountService service; // 서비스 객체 (테스트 대상)

    @Test
    @DisplayName("잔액 조회 - 계좌가 존재할 때")
    void getBalance_success() {
        // given
        Long userId = 1L;
        Account account = accountWithAmount(userId, 1000);
        when(repository.findByUserId(userId)).thenReturn(Optional.of(account)); // mock repository

        // when
        BigDecimal balance = service.getBalance(userId); // 실제 테스트 로직

        // then
        assertThat(balance).isEqualByComparingTo("1000");
    }

    @Test
    @DisplayName("잔액 조회 - 계좌가 존재하지 않으면 예외 발생")
    void getBalance_notFound_throwsException() {
        // given
        Long userId = 1L;
        when(repository.findByUserId(userId)).thenReturn(Optional.empty()); // mock repository, 계좌 없음

        // when & then
        assertThrows(IllegalArgumentException.class, () -> service.getBalance(userId)); // 예외 발생 검증
    }

    @Test
    @DisplayName("잔액 충전 성공")
    void charge_success() {
        // given
        Long userId = 1L;
        Account account = accountWithAmount(userId, 1000);
        when(repository.findByUserId(userId)).thenReturn(Optional.of(account)); // mock repository

        // when
        service.charge(userId, BigDecimal.valueOf(500)); // 실제 테스트 로직

        // then
        assertThat(account.getAmount()).isEqualByComparingTo("1500"); // 충전 결과 검증
        verify(repository).save(account); // 저장 호출 여부 검증
    }

    @Test
    @DisplayName("잔액 사용 성공")
    void use_success() {
        // given
        Long userId = 1L;
        Account account = accountWithAmount(userId, 1000);
        when(repository.findByUserId(userId)).thenReturn(Optional.of(account)); // mock repository

        // when
        service.use(userId, BigDecimal.valueOf(500)); // 실제 테스트 로직

        // then
        assertThat(account.getAmount()).isEqualByComparingTo("500"); // 차감 결과 검증
        verify(repository).save(account); // 저장 호출 여부 검증
    }

    private Account accountWithAmount(Long userId, int amount) {
        User user = new User(userId, "tester", "token123"); // 테스트용 유저 객체
        return new Account(100L, user, BigDecimal.valueOf(amount)); // Account 객체 생성
    }
}
