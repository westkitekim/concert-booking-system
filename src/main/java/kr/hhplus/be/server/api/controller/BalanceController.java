package kr.hhplus.be.server.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.api.controller.dto.request.BalanceRequest;
import kr.hhplus.be.server.api.controller.dto.response.BalanceResponse;
import kr.hhplus.be.server.api.controller.dto.response.SimpleErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/balance")
@Tag(name = "Balance", description = "잔액 API")
public class BalanceController {

    @PutMapping("/charge")
    @Operation(summary = "잔액 충전", description = "사용자에게 잔액을 충전합니다.")
    @ApiResponse(responseCode = "200", description = "정상 응답",
            content = @Content(schema = @Schema(implementation = BalanceResponse.class)))
    public ResponseEntity<BalanceResponse> charge(
            @RequestBody(required = true) BalanceRequest request) {

        BalanceResponse response = new BalanceResponse(
                request.userId(),
                request.amount(),
                10_000
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "잔액 조회", description = "사용자 잔액 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답",
                    content = @Content(schema = @Schema(implementation = BalanceResponse.class))),
            @ApiResponse(responseCode = "404", description = "유저 정보 없음",
                    content = @Content(schema = @Schema(implementation = SimpleErrorResponse.class)))
    })
    public ResponseEntity<?> getBalance(@PathVariable Long userId) {

        if (userId == 0) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new SimpleErrorResponse("유저를 찾을 수 없습니다"));
        }
        BalanceResponse response = new BalanceResponse(userId, 0, 5_000);
        return ResponseEntity.ok(response);
    }
}

