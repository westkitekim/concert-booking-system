package kr.hhplus.be.server.domain.concert.dto;

import kr.hhplus.be.server.domain.concert.Concert;
import java.util.List;

public record ConcertInfo(
        Long concertId,
        String concertTitle,
        List<VenueScheduleInfo> venueSchedules
) {
    public static ConcertInfo of(Concert concert, List<VenueScheduleInfo> venueSchedules) {
        return new ConcertInfo(
                concert.getId(),
                concert.getTitle(),
                venueSchedules
        );
    }
}



