package kr.hhplus.be.server.domain.account;

import kr.hhplus.be.server.domain.user.User;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Account {

    private final Long accountId;
    private final User user;
    private BigDecimal amount;

    public Account(Long accountId, User user, BigDecimal amount) {
        this.accountId = accountId;
        this.user = user;
        this.amount = (amount != null) ? amount : BigDecimal.ZERO;
    }

    public void add(BigDecimal point) {
        validatePositive(point);
        this.amount = this.amount.add(point);
    }

    public void subtract(BigDecimal point) {
        validatePositive(point);
        validateEnough(point);
        this.amount = this.amount.subtract(point);
    }

    private void validatePositive(BigDecimal point) {
        if (point == null || point.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("0 이하의 금액은 사용할 수 없습니다.");
        }
    }

    private void validateEnough(BigDecimal point) {
        if (this.amount.compareTo(point) < 0) {
            throw new IllegalArgumentException("잔액이 부족합니다.");
        }
    }
}

