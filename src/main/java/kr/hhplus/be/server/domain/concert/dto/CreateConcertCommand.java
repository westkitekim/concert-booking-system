package kr.hhplus.be.server.domain.concert.dto;

import java.time.LocalDateTime;

public record CreateConcertCommand(
        String title,
        String description,
        Long venueId,
        LocalDateTime startAt
) {}

