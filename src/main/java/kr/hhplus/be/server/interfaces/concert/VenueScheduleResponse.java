package kr.hhplus.be.server.interfaces.concert;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record VenueScheduleResponse(
        @Schema(description = "공연 장소 ID") Long venueId,
        @Schema(description = "공연 장소 이름") String venueName,
        @Schema(description = "해당 장소의 스케줄 목록") List<ScheduleSeatResponse> schedules
) {
}
