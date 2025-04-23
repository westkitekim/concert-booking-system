package kr.hhplus.be.server.infrastructure.account;

import kr.hhplus.be.server.domain.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaAccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUserId(Long userId);
}
