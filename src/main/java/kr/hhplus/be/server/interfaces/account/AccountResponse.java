package kr.hhplus.be.server.interfaces.account;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hhplus.be.server.domain.account.Account;
import kr.hhplus.be.server.domain.account.AccountBalanceInfo;

import java.math.BigDecimal;

@Schema(description = "잔액 충전 응답 DTO")
public record AccountResponse(

        @Schema(description = "사용자 ID", example = "1")
        Long userId,

        @Schema(description = "충전된 금액", example = "5000")
        Long accountId,

        @Schema(description = "현재 잔액", example = "10000")
        BigDecimal currentBalance

) {
        public static AccountResponse from(AccountBalanceInfo info) {
                return new AccountResponse(info.userId(), info.accountId(), info.balance());
        }
}
