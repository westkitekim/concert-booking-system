package kr.hhplus.be.server.domain.concert;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hhplus.be.server.domain.seat.Seat;
import kr.hhplus.be.server.domain.seat.SeatStatusEnum;

public record SeatInfo(
        @Schema(description = "좌석 번호", example = "1")
        String seatId,
        @Schema(description = "예약 가능 여부", example = "true")
        boolean available,
        @Schema(description = "보유 유저 ID", example = "user123")
        String userId
) {
        public static SeatInfo of(Seat seat) {
                return new SeatInfo(
                        seat.getSeatId().toString(),
                        seat.getSeatStatus() == SeatStatusEnum.AVAILABLE,
                        null // userId는 예약 도메인과 연결되는 경우만 설정
                );
        }
}
