package kr.hhplus.be.server.api.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "잔액 충전 응답 DTO")
public record BalanceResponse(

        @Schema(description = "사용자 ID", example = "1")
        Long userId,

        @Schema(description = "충전된 금액", example = "5000")
        Integer chargedAmount,

        @Schema(description = "현재 잔액", example = "10000")
        Integer currentBalance

) {}
