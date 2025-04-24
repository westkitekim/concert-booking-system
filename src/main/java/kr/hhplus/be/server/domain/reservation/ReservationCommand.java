package kr.hhplus.be.server.domain.reservation;

import kr.hhplus.be.server.interfaces.reservation.ReservationRequest;

import java.math.BigDecimal;

public record ReservationCommand(
        String token,
        Long userId,
        Long concertId,
        Long scheduleId,
        Long seatId,
        BigDecimal amount
) {
    public static ReservationCommand of(ReservationRequest request) {
        return new ReservationCommand(
                request.token(),
                request.userId(),
                request.concertId(),
                request.scheduleId(),
                request.seatId(),
                request.amount()
        );
    }

}

