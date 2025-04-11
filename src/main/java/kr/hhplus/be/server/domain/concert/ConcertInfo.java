package kr.hhplus.be.server.domain.concert;

import kr.hhplus.be.server.domain.schedule.Schedule;
import kr.hhplus.be.server.domain.seat.Seat;
import kr.hhplus.be.server.domain.seat.SeatStatusEnum;
import kr.hhplus.be.server.domain.venue.Venue;

import java.time.LocalDateTime;
import java.util.List;

public record ConcertInfo(
        Long concertId,
        String concertTitle,
        String venueName,
        LocalDateTime scheduleTime,
        List<SeatInfo> seats  // 좌석 리스트
) {
    public static ConcertInfo of(Concert concert, Schedule schedule, Venue venue, List<Seat> seats) {
        return new ConcertInfo(
                concert.getId(),
                concert.getTitle(),
                venue.getName(),
                schedule.getStartAt(),
                seats.stream()
                        .map(SeatInfo::of)
                        .toList()
        );
    }
}


