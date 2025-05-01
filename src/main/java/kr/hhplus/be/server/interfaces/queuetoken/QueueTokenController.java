package kr.hhplus.be.server.interfaces.queuetoken;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import kr.hhplus.be.server.domain.queuetoken.QueueToken;
import kr.hhplus.be.server.domain.queuetoken.QueueTokenService;
import kr.hhplus.be.server.domain.queuetoken.QueueTokenStatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/v1/queue")
@Tag(name = "QueueToken", description = "대기열 API")
@RequiredArgsConstructor
public class QueueTokenController {

    private final QueueTokenService queueTokenService;

    @PostMapping("/token")
    @Operation(summary = "대기열 토큰 발급", description = "UUID 기반 토큰 생성 후 대기열 입장")
    @ApiResponse(responseCode = "200", description = "정상 발급",
            content = @Content(schema = @Schema(implementation = QueueTokenResponse.class)))
    public ResponseEntity<QueueTokenResponse> issueToken(@RequestBody @Valid QueueTokenRequest request) {
        QueueToken token = queueTokenService.issueToken(request.username());
        return ResponseEntity.ok(QueueTokenResponse.from(token));
    }

    @GetMapping("/status/{token}")
    @Operation(summary = "대기열 토큰 상태 조회", description = "토큰 상태(PENDING, READY 등)를 조회합니다.")
    public ResponseEntity<QueueTokenStatusEnum> getTokenStatus(@PathVariable String token) {
        QueueTokenStatusEnum status = queueTokenService.getStatus(token);
        return ResponseEntity.ok(status);
    }
}

