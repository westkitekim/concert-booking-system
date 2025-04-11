package kr.hhplus.be.server.interfaces.queue;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record QueueTokenRequest(
        @JsonProperty("username")
        @Schema(description = "사용자 이름", example = "westkite")
        @NotBlank(message = "username은 필수입니다.")
        String username
) {}
