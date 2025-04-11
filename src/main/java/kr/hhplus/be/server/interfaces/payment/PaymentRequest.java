package kr.hhplus.be.server.interfaces.payment;

import java.math.BigDecimal;

public record PaymentRequest(
        String token,
        Long userId,
        Long concertId,
        Long scheduleId,
        Long seatId,
        BigDecimal amount
) {}

