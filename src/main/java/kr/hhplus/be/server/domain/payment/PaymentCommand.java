package kr.hhplus.be.server.domain.payment;

import jakarta.validation.Valid;
import kr.hhplus.be.server.domain.reservation.ReservationCommand;
import kr.hhplus.be.server.interfaces.payment.PaymentRequest;

import java.math.BigDecimal;

public record PaymentCommand(
        String token,
        Long userId,
        Long concertId,   // 🔥 추가
        Long scheduleId,
        Long seatId,
        BigDecimal amount
) {
    public static PaymentCommand of(@Valid PaymentRequest request) {
        return new PaymentCommand(
                request.token(),
                request.userId(),
                request.concertId(),
                request.scheduleId(),
                request.seatId(),
                request.amount()
        );
    }

    public static PaymentCommand of(ReservationCommand command) {
        return new PaymentCommand(
                command.token(),
                command.userId(),
                command.scheduleId(),
                command.seatId(),
                command.amount()
        );
    }

}


