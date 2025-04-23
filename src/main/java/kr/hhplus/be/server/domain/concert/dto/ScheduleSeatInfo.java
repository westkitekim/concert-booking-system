package kr.hhplus.be.server.domain.concert.dto;

import kr.hhplus.be.server.domain.schedule.Schedule;
import kr.hhplus.be.server.domain.seat.Seat;

import java.time.LocalDateTime;
import java.util.List;

public record ScheduleSeatInfo(
        Long scheduleId,
        LocalDateTime startAt,
            List<SeatInfo> seats
) {
    public static ScheduleSeatInfo of(Schedule schedule, List<Seat> seats) {
        return new ScheduleSeatInfo(
                schedule.getId(),
                schedule.getStartAt(),
                seats.stream().map(SeatInfo::of).toList()
        );
    }
}
