package kr.hhplus.be.server.interfaces.payment;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hhplus.be.server.domain.payment.PaymentInfo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "결제 응답 DTO")
public record PaymentResponse(

        @Schema(description = "결제 ID")
        Long paymentId,

        @Schema(description = "유저 ID")
        Long userId,

        @Schema(description = "콘서트 ID")
        Long concertId,

        @Schema(description = "공연 회차 ID")
        Long scheduleId,

        @Schema(description = "좌석 ID")
        Long seatId,

        @Schema(description = "결제 금액")
        BigDecimal amount,

        @Schema(description = "결제 시각")
        LocalDateTime paidAt

) {
        public static PaymentResponse from(PaymentInfo info) {
                return new PaymentResponse(
                        info.paymentId(),
                        info.userId(),
                        info.concertId(),
                        info.scheduleId(),
                        info.seatId(),
                        info.amount(),
                        info.paidAt()
                );
        }
}
