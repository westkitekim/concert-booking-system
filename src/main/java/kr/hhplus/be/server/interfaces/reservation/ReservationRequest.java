package kr.hhplus.be.server.interfaces.reservation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ReservationRequest(
        @Schema(description = "토큰", example = "EKJKE-EKFJLE")
        @NotBlank String token,
        @Schema(description = "유저 ID", example = "user123")
        @NotBlank Long userId,
        @Schema(description = "콘서트 ID", example = "concert1")
        @NotBlank Long concertId,
        @Schema(description = "날짜", example = "2025-04-10")
        @NotNull LocalDate date,
        @Schema(description = "좌석 번호", example = "1")
        @NotBlank String seatNumber,
        @Schema(description = "스케줄 ID", example = "1001")
        @NotNull Long scheduleId,
        @Schema(description = "좌석 ID", example = "2001")
        @NotNull Long seatId,
        @Schema(description = "결제 금액", example = "10000")
        @NotNull BigDecimal amount
) {}
