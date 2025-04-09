package kr.hhplus.be.server.api.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record QueueTokenRequest(
        @Schema(description = "유저 ID", example = "user123")
        @NotBlank String userId
) {}
