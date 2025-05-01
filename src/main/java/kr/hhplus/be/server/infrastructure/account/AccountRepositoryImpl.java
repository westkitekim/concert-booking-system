package kr.hhplus.be.server.infrastructure.account;

import kr.hhplus.be.server.domain.account.Account;
import kr.hhplus.be.server.domain.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {

    private final JpaAccountRepository jpaAccountRepository;

    @Override
    public Optional<Account> findByUserId(Long userId) {
        return jpaAccountRepository.findByUserId(userId);
    }

    @Override
    public void save(Account account) {
        jpaAccountRepository.save(account);
    }
}
