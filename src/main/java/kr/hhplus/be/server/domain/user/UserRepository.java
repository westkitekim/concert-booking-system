package kr.hhplus.be.server.domain.user;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {

    public Optional<User> findById(Long userId);

    public User save(User user);

    public Optional<User> findLastUserId();
}
