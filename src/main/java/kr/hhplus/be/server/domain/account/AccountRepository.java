package kr.hhplus.be.server.domain.account;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository {

    Optional<Account> findByUserId(Long userId);

    void save(Account userPoint);
}
