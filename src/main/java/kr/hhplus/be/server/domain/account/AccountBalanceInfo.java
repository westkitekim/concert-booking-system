package kr.hhplus.be.server.domain.account;

import kr.hhplus.be.server.domain.user.User;

import java.math.BigDecimal;

public record AccountBalanceInfo(
        Long userId,
        String username,
        String queueToken,
        BigDecimal balance
) {
    public static AccountBalanceInfo from(Account account) {

        User user = account.getUser();

        return new AccountBalanceInfo(
                user.getUserId(),
                user.getUsername(),
                user.getQueueToken(),
                account.getAmount()
        );
    }
}

