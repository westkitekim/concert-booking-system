package kr.hhplus.be.server.domain.reservation;

import kr.hhplus.be.server.domain.seat.Seat;

import java.time.LocalDateTime;

public record ReservationInfo(
        Long userId,
        Long scheduleId,
        Long seatId,
        String seatStatus,
        LocalDateTime reservedAt
) {
    public static ReservationInfo of(Reservation reservation, Seat seat) {
        return new ReservationInfo(
                reservation.getUserId(),
                reservation.getScheduleId(),
                Long.valueOf(reservation.getSeatId()),
                seat.getSeatStatus().name(),
                reservation.getReservedAt()
        );
    }

    public static ReservationInfo of(ReservationCommand command, Seat seat) {
        return new ReservationInfo(
                command.userId(),
                command.scheduleId(),
                command.seatId(),
                seat.getSeatStatus().name(),
                null  // reservedAt은 hold 단계에서는 없음
        );
    }
}

