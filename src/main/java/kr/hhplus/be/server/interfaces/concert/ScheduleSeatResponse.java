package kr.hhplus.be.server.interfaces.concert;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

public record ScheduleSeatResponse(
        @Schema(description = "스케줄 ID") Long scheduleId,
        @Schema(description = "스케줄 시작 시간") LocalDateTime startAt,
        @Schema(description = "좌석 목록") List<SeatResponse> seats
) {
}
