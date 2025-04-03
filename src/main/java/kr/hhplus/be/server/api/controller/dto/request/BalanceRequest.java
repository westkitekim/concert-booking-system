package kr.hhplus.be.server.api.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonProperty;

@Schema(description = "잔액 DTO")
public record BalanceRequest(
        @Schema(description = "유저 ID", example = "1")
        @JsonProperty(required = true)
        Long userId,

        @Schema(description = "충전 금액", example = "5000")
        @JsonProperty(required = true)
        Integer amount
) {}

