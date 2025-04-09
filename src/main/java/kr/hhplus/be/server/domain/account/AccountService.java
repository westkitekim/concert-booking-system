package kr.hhplus.be.server.domain.account;

import kr.hhplus.be.server.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;

    public BigDecimal getBalance(Long userId) {
        return repository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 계정 정보가 없습니다."))
                .getAmount();
    }

    public void charge(Long userId, BigDecimal amount) {
        Account account = repository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 계정 정보가 없습니다."));
        account.add(amount);
        repository.save(account);
    }

    public void use(Long userId, BigDecimal amount) {
        Account account = repository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 계정 정보가 없습니다."));
        account.subtract(amount);
        repository.save(account);
    }

}
