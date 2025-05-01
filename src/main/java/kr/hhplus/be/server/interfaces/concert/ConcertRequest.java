package kr.hhplus.be.server.interfaces.concert;

import java.time.LocalDateTime;

public record ConcertRequest(
        String title,
        Long venueId,
        LocalDateTime from,
        LocalDateTime to
) {}
