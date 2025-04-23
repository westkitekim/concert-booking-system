package kr.hhplus.be.server.domain.payment;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

@Entity
@Table(name = "payment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long concertId;

    @Column(nullable = false)
    private Long scheduleId;

    @Column(nullable = false)
    private Long seatId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDateTime paidAt;

    private Payment(Long userId, Long concertId, Long scheduleId, Long seatId, BigDecimal amount, LocalDateTime paidAt) {
        this.userId = userId;
        this.concertId = concertId;
        this.scheduleId = scheduleId;
        this.seatId = seatId;
        this.amount = amount;
        this.paidAt = paidAt;
    }

    public static Payment of(Long userId, Long concertId, Long scheduleId, Long seatId, BigDecimal amount) {
        return new Payment(
                userId,
                concertId,
                scheduleId,
                seatId,
                amount,
                LocalDateTime.now());
    }
}

