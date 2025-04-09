package kr.hhplus.be.server.domain.account;

import kr.hhplus.be.server.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountTest {

    @Test
    @DisplayName("잔액 조회")
    void getAmount_success() {
        // given
        Account account = fakeAccount(1000);  // 1000원 잔액 계좌 생성

        // when
        BigDecimal amount = account.getAmount();  // 잔액 조회

        // then
        assertThat(amount).isEqualByComparingTo("1000");  // 1000원이 정확히 반환되는지 검증
    }

    @Test
    @DisplayName("잔액 충전 성공")
    void add_success() {
        // given
        Account account = fakeAccount(1000);

        // when
        account.add(BigDecimal.valueOf(500));

        // then
        assertThat(account.getAmount()).isEqualByComparingTo("1500");
    }

    @Test
    @DisplayName("잔액 차감 성공")
    void subtract_success() {
        // given
        Account account = fakeAccount(10000);

        // when
        account.subtract(BigDecimal.valueOf(500));

        // then
        assertThat(account.getAmount()).isEqualByComparingTo("500");
    }

    @Test
    @DisplayName("잔액 부족시 예외 발생")
    void subtract_insufficient_balance() {
        // given
        Account account = fakeAccount(1000);

        // when & then
        assertThrows(IllegalArgumentException.class, () ->
                account.subtract(BigDecimal.valueOf(2000))); // 잔액 부족 예외
    }

    @Test
    @DisplayName("0원 이하 충전 시 예외 발생")
    void add_zeroOrNegative() {
        // given
        Account account = fakeAccount(1000);

        // when & then
        assertThrows(IllegalArgumentException.class, () ->
                account.add(BigDecimal.valueOf(-100)));
    }

    @Test
    @DisplayName("계좌 생성 확인")
    void account_creation() {
        // given
        User user = new User(1L, "tester", "token123");

        // when
        Account account = new Account(10L, user, BigDecimal.valueOf(1000));

        // then
        assertThat(account.getUser()).isEqualTo(user);
        assertThat(account.getAmount()).isEqualByComparingTo("1000");
    }

    private Account fakeAccount(int amount) {
        User user = new User(1L, "tester", "token123");
        return new Account(10L, user, BigDecimal.valueOf(amount));
    }
}
