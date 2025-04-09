package kr.hhplus.be.server.interfaces.queue;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record QueueTokenRequest(
        @JsonProperty("userId")
        @Schema(description = "유저 ID", example = "user123")
        @NotBlank
        String userId
) {}
