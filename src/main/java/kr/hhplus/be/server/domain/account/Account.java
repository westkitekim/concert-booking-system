package kr.hhplus.be.server.domain.account;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.TestOnly;

import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private BigDecimal amount;

    public Account(Long userId, BigDecimal amount) {
        this.userId = userId;
        this.amount = amount != null ? amount : BigDecimal.ZERO;
    }

    @TestOnly
    public Account(Long accountId, Long userId, BigDecimal amount) {
        this.userId = userId;
        this.amount = amount != null ? amount : BigDecimal.ZERO;
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
