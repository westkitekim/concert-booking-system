package kr.hhplus.be.server.domain.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;

    // 잔액 조회
    public AccountBalanceInfo getBalance(Long userId) {
        Account account = repository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 계정 정보가 없습니다."));

        return AccountBalanceInfo.of(account);
    }

    // 잔액 충전
    public AccountBalanceInfo charge(ChargeAccountCommand command) {
        Account account = repository.findByUserId(command.userId())
                .orElseThrow(() -> new IllegalArgumentException("유저 계정 정보가 없습니다."));

        account.add(command.amount());
        repository.save(account);

        return AccountBalanceInfo.of(account);
    }

    // 잔액 사용
    public void use(Long userId, BigDecimal amount) {
        Account account = repository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 계정 정보가 없습니다."));

        account.subtract(amount);
        repository.save(account);
    }

}
