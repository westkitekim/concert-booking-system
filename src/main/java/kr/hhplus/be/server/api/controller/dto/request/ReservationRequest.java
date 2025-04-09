package kr.hhplus.be.server.api.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ReservationRequest(
        @Schema(description = "유저 ID", example = "user123")
        @NotBlank String userId,
        @Schema(description = "콘서트 ID", example = "concert1")
        @NotBlank String concertId,
        @Schema(description = "날짜", example = "2025-04-10")
        @NotNull LocalDate date,
        @Schema(description = "좌석 번호", example = "1")
        @NotBlank String seatNumber
) {}

