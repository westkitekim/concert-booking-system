package kr.hhplus.be.server.interfaces.account;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.domain.account.AccountBalanceInfo;
import kr.hhplus.be.server.domain.account.AccountService;
import kr.hhplus.be.server.domain.account.ChargeAccountCommand;
import kr.hhplus.be.server.interfaces.SimpleErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/account")
@Tag(name = "Balance", description = "잔액 API")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/charge")
    @Operation(summary = "잔액 충전", description = "사용자에게 잔액을 충전합니다.")
    @ApiResponse(responseCode = "200", description = "정상 응답",
            content = @Content(schema = @Schema(implementation = AccountResponse.class)))
    public ResponseEntity<AccountResponse> charge(
            @RequestBody AccountRequest request) {

        AccountBalanceInfo info = accountService.charge(new ChargeAccountCommand(request.userId(), request.amount()));
        AccountResponse response = AccountResponse.from(info);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/use")
    @Operation(summary = "잔액 사용", description = "사용자 잔액을 사용합니다.")
    @ApiResponse(responseCode = "200", description = "정상 응답",
            content = @Content(schema = @Schema(implementation = AccountResponse.class)))
    public ResponseEntity<AccountResponse> use(
            @RequestBody AccountRequest request) {

        accountService.use(request.userId(), request.amount());
        AccountBalanceInfo info = accountService.getBalance(request.userId());
        return ResponseEntity.ok(AccountResponse.from(info));
    }

    @GetMapping("/{userId}/balance")
    @Operation(summary = "잔액 조회", description = "사용자 잔액 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답",
                    content = @Content(schema = @Schema(implementation = AccountResponse.class))),
            @ApiResponse(responseCode = "404", description = "유저 정보 없음",
                    content = @Content(schema = @Schema(implementation = SimpleErrorResponse.class)))
    })
    public ResponseEntity<?> getBalance(@PathVariable Long userId) {

        AccountBalanceInfo info = accountService.getBalance(userId);
        return ResponseEntity.ok(AccountResponse.from(info));
    }
}

