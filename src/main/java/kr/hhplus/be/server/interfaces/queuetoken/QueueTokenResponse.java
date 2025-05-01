package kr.hhplus.be.server.interfaces.queuetoken;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.hhplus.be.server.domain.queuetoken.QueueToken;
import kr.hhplus.be.server.domain.queuetoken.QueueTokenStatusEnum;

public record QueueTokenResponse(
        @JsonProperty("token")
        @Schema(description = "대기열 토큰", example = "a5f3c6...-uuid-value")
        String token,

        @JsonProperty("userId")
        @Schema(description = "유저 ID", example = "123")
        long userId,

        @JsonProperty("position")
        @Schema(description = "대기열 내 순번", example = "7")
        int position,

        @JsonProperty("status")
        @Schema(description = "현재 상태", example = "PENDING")
        QueueTokenStatusEnum status
) {
        public static QueueTokenResponse from(QueueToken token) {
                return new QueueTokenResponse(
                        token.getToken(),
                        token.getUserId(),
                        token.getPosition(),
                        token.getTokenStatus()
                );
        }
}
