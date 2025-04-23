package kr.hhplus.be.server.interfaces.concert;

import kr.hhplus.be.server.domain.concert.dto.ConcertInfo;
import org.springframework.stereotype.Component;

@Component
public class ConcertResponseMapper {
    public ConcertResponse toResponse(ConcertInfo info) {
        return new ConcertResponse(
                info.concertId(),
                info.concertTitle(),
                info.venueSchedules().stream().map(vs -> new VenueScheduleResponse(
                        vs.venueId(),
                        vs.venueName(),
                        vs.schedules().stream().map(ss -> new ScheduleSeatResponse(
                                ss.scheduleId(),
                                ss.startAt(),
                                ss.seats().stream().map(seat -> new SeatResponse(
                                        seat.seatId(),
                                        seat.available(),
                                        seat.userId()
                                )).toList()
                        )).toList()
                )).toList()
        );
    }
}

