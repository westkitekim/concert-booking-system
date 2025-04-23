package kr.hhplus.be.server.domain.user;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {

    Optional<User> findByQueueToken(String queueToken);

    Optional<User> findById(Long id);

    User save(User user);
}
