package kr.hhplus.be.server.interfaces.account;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@Schema(description = "잔액 DTO")
public record AccountRequest(
        @Schema(description = "유저 ID", example = "1")
        @JsonProperty(required = true)
        Long userId,

        @Schema(description = "충전 금액", example = "5000")
        @JsonProperty(required = true)
        BigDecimal amount
) {}

