package kr.hhplus.be.server.interfaces.queue;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/queue")
@Tag(name = "QueueToken", description = "대기열 API")
public class QueueTokenController {

    private static final Map<String, QueueTokenResponse> issuedTokens = new HashMap<>();

    @PostMapping("/token")
    @Operation(summary = "대기열 토큰 발급", description = "UUID 기반 토큰 생성 후 대기열 입장")
    @ApiResponse(responseCode = "200", description = "정상 발급", content = @Content(schema = @Schema(implementation = QueueTokenResponse.class)))
    public ResponseEntity<QueueTokenResponse> issueToken(@RequestBody @Valid QueueTokenRequest request) {
        if (issuedTokens.containsKey(request.userId())) {
            return ResponseEntity.ok(issuedTokens.get(request.userId()));
        }
        QueueTokenResponse token = new QueueTokenResponse(UUID.randomUUID().toString(), request.userId(), 1);
        issuedTokens.put(request.userId(), token);
        return ResponseEntity.ok(token);
    }
}
