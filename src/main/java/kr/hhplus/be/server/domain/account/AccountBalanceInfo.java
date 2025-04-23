package kr.hhplus.be.server.domain.account;

import java.math.BigDecimal;

public record AccountBalanceInfo(
        Long userId,
        Long accountId,
        String username,
        String queueToken,
        BigDecimal balance
) {
    public static AccountBalanceInfo of(Account account) {

        return new AccountBalanceInfo(
                account.getUserId(),
                account.getAccountId(),
                null, // user.getUsername(),
                null, // user.getQueueToken(),
                account.getAmount()
        );
    }
}

