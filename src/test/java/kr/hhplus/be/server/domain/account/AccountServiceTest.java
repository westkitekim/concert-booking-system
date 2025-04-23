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
    AccountRepository repository;

    @Mock
    User user;

    @InjectMocks
    AccountService service;

    @Test
    @DisplayName("잔액 조회 - 계좌가 존재할 때")
    void getBalance_success() {
        // given
        Long userId = 1L;
        Account account = accountWithAmount(userId, 1000);
        when(repository.findByUserId(userId)).thenReturn(Optional.of(account));

        // when
        AccountBalanceInfo info = service.getBalance(userId);

        // then
        assertThat(info.balance()).isEqualByComparingTo("1000");
    }

    @Test
    @DisplayName("잔액 조회 - 계좌가 존재하지 않으면 예외 발생")
    void getBalance_notFound_throwsException() {
        // given
        Long userId = 1L;
        when(repository.findByUserId(userId)).thenReturn(Optional.empty());

        // when & then
        assertThrows(IllegalArgumentException.class, () -> service.getBalance(userId));
    }

    @Test
    @DisplayName("잔액 충전 성공")
    void charge_success() {
        // given
        Long userId = 1L;
        Account account = accountWithAmount(userId, 1000);
        when(repository.findByUserId(userId)).thenReturn(Optional.of(account));

        // when
        service.charge(new ChargeAccountCommand( userId, BigDecimal.valueOf(500)));

        // then
        assertThat(account.getAmount()).isEqualByComparingTo("1500");
        verify(repository).save(account);
    }

    @Test
    @DisplayName("잔액 사용 성공")
    void use_success() {
        // given
        Long userId = 1L;
        Account account = accountWithAmount(userId, 1000);
        when(repository.findByUserId(userId)).thenReturn(Optional.of(account));

        // when
        service.use(userId, BigDecimal.valueOf(500));

        // then
        assertThat(account.getAmount()).isEqualByComparingTo("500");
        verify(repository).save(account);
    }

    private Account accountWithAmount(Long userId, int amount) {
        return new Account(100L, userId, BigDecimal.valueOf(amount));
    }
}
