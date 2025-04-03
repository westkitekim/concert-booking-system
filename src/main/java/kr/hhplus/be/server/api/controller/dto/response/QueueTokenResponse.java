package kr.hhplus.be.server.api.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record QueueTokenResponse(
        @Schema(description = "대기열 토큰", example = "cde98c38-cc90-4dc6-b7fd-8291e421ab5a")
        String token,
        @Schema(description = "유저 ID", example = "user123")
        String userId,
        @Schema(description = "대기열 위치", example = "1")
        int position
) {}
