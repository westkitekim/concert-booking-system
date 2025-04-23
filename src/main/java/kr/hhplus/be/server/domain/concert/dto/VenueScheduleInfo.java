package kr.hhplus.be.server.domain.concert.dto;

import kr.hhplus.be.server.domain.venue.Venue;

import java.util.List;

public record VenueScheduleInfo(
        Long venueId,
        String venueName,
        List<ScheduleSeatInfo> schedules
) {
    public static VenueScheduleInfo of(Venue venue, List<ScheduleSeatInfo> schedules) {
        return new VenueScheduleInfo(venue.getId(), venue.getName(), schedules);
    }
}

