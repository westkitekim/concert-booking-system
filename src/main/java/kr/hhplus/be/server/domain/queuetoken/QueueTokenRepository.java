package kr.hhplus.be.server.domain.queuetoken;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface QueueTokenRepository {

    QueueToken save(QueueToken token);

    Optional<Integer> findMaxPosition();

    Optional<QueueToken> findByToken(String token);

    List<QueueToken> findAllByStatus(QueueTokenStatusEnum status);

    List<QueueToken> findExpiredTokens(LocalDateTime now);

    Optional<QueueToken> findReadyTokenWithMinPosition();

    boolean existsByUserId(Long userId);
}


