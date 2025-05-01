package kr.hhplus.be.server.interfaces.concert;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record ConcertResponse(
        @Schema(description = "콘서트 ID") Long concertId,
        @Schema(description = "콘서트 제목") String concertTitle,
        @Schema(description = "공연 장소별 스케줄") List<VenueScheduleResponse> venueSchedules
) {
}
