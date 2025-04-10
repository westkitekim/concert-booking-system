package kr.hhplus.be.server.domain.queuetoken;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QueueTokenRepository {

    void save(QueueToken token);

    Optional<Integer> findMaxPosition();

    QueueToken findByToken(String token);

    List<QueueToken> findAllByStatus(QueueTokenStatusEnum status);

    List<QueueToken> findAll();
}


