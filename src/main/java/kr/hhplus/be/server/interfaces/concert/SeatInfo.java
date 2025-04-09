package kr.hhplus.be.server.interfaces.concert;

import io.swagger.v3.oas.annotations.media.Schema;

public record SeatInfo(
        @Schema(description = "좌석 번호", example = "1")
        String seatNumber,
        @Schema(description = "예약 가능 여부", example = "true")
        boolean available,
        @Schema(description = "보유 유저 ID", example = "user123")
        String userId
) {}
