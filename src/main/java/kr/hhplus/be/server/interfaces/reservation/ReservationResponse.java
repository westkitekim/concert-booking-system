package kr.hhplus.be.server.interfaces.reservation;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hhplus.be.server.domain.reservation.ReservationInfo;

import java.time.LocalDateTime;

public record ReservationResponse(
        @Schema(description = "userId", example = "12L")
        String userId,
        @Schema(description = "유효 시간(분)", example = "5")
        LocalDateTime validMinutes
) {
        public static ReservationResponse of(ReservationInfo info) {
                return new ReservationResponse("reserved", LocalDateTime.now()); // 유효시간은 정책에 따라 상수로 처리
        }

        public static ReservationResponse from(ReservationInfo info) {
                return new ReservationResponse(
                        info.seatStatus(),
                        info.reservedAt()
                );
        }
}
