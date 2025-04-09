package kr.hhplus.be.server.interfaces.payment;

import io.swagger.v3.oas.annotations.media.Schema;

public record PaymentResponse(
        @Schema(description = "결제 결과", example = "success")
        String result,
        @Schema(description = "좌석 소유자 ID", example = "user123")
        String ownerUserId,
        @Schema(description = "좌석 번호", example = "1")
        String seatNumber
) {}

