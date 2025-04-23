package kr.hhplus.be.server.interfaces.concert;

import io.swagger.v3.oas.annotations.media.Schema;

public record SeatResponse(
        @Schema(description = "좌석 번호") String seatId,
        @Schema(description = "예약 가능 여부") boolean available,
        @Schema(description = "보유자 ID") String userId // optional
) {
}