package kr.hhplus.be.server.interfaces.queue;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public record QueueTokenResponse(
        @JsonProperty("token")
        @Schema(description = "대기열 토큰", example = "cde98c38-cc90-4dc6-b7fd-8291e421ab5a")
        String token,

        @JsonProperty("userId")
        @Schema(description = "유저 ID", example = "user123")
        String userId,

        @JsonProperty("position")
        @Schema(description = "대기열 위치", example = "1")
        int position
) {}
