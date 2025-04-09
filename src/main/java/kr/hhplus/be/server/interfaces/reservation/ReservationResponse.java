package kr.hhplus.be.server.interfaces.reservation;

import io.swagger.v3.oas.annotations.media.Schema;

public record ReservationResponse(
        @Schema(description = "예약 상태", example = "reserved")
        String status,
        @Schema(description = "유효 시간(분)", example = "5")
        int validMinutes
) {}
