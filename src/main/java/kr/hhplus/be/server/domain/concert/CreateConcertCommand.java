package kr.hhplus.be.server.domain.concert;

import java.time.LocalDateTime;

public record CreateConcertCommand(
        String title,
        String description,
        Long venueId,
        LocalDateTime startAt
) {}

