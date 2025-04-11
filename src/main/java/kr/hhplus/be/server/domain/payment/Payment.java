package kr.hhplus.be.server.domain.payment;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

@Getter
public class Payment {

    private static final AtomicLong generator = new AtomicLong();

    private final Long paymentId;
    private final Long userId;
    private final Long concertId;
    private final Long scheduleId;
    private final Long seatId;
    private final BigDecimal amount;
    private final LocalDateTime paidAt;

    public static Payment of(Long userId, Long concertId, Long scheduleId, Long seatId, BigDecimal amount) {
        return new Payment(
                generator.incrementAndGet(),
                userId,
                concertId,
                scheduleId,
                seatId,
                amount,
                LocalDateTime.now());
    }

    private Payment(Long paymentId, Long userId, Long concertId, Long scheduleId, Long seatId, BigDecimal amount, LocalDateTime paidAt) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.concertId = concertId;
        this.scheduleId = scheduleId;
        this.seatId = seatId;
        this.amount = amount;
        this.paidAt = paidAt;
    }
}

