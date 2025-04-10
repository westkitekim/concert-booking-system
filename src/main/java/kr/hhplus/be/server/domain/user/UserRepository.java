package kr.hhplus.be.server.domain.user;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {

    Optional<User> findById(Long userId);

    User save(User user);

    Optional<User> findLastUserId();
}
