package kr.hhplus.be.server.interfaces.account;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "잔액 충전 응답 DTO")
public record AccountResponse(

        @Schema(description = "사용자 ID", example = "1")
        Long userId,

        @Schema(description = "충전된 금액", example = "5000")
        BigDecimal chargedAmount,

        @Schema(description = "현재 잔액", example = "10000")
        BigDecimal currentBalance

) {}
