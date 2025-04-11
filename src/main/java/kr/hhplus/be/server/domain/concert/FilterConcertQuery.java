package kr.hhplus.be.server.domain.concert;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record FilterConcertQuery(
        String title,
        Long venueId,
        LocalDateTime from,
        LocalDateTime to
) {}

