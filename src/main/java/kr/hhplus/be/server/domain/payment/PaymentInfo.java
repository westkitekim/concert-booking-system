package kr.hhplus.be.server.domain.payment;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentInfo(
        Long paymentId,
        Long userId,
        Long concertId,
        Long scheduleId,
        Long seatId,
        BigDecimal amount,
        LocalDateTime paidAt
) {
    public static PaymentInfo from(Payment payment) {
        return new PaymentInfo(
                payment.getPaymentId(),
                payment.getUserId(),
                payment.getConcertId(),
                payment.getScheduleId(),
                payment.getSeatId(),
                payment.getAmount(),
                payment.getPaidAt()
        );
    }
}

