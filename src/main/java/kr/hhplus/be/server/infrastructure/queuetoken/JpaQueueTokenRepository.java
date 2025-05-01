package kr.hhplus.be.server.infrastructure.queuetoken;

import kr.hhplus.be.server.domain.queuetoken.QueueToken;
import kr.hhplus.be.server.domain.queuetoken.QueueTokenStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface JpaQueueTokenRepository extends JpaRepository<QueueToken, Long> {

    Optional<QueueToken> findByToken(String token);

    List<QueueToken> findAllByTokenStatus(QueueTokenStatusEnum status);

    List<QueueToken> findAllByExpiresAtBefore(OffsetDateTime now);

    Optional<QueueToken> findTopByTokenStatusOrderByPositionAsc(QueueTokenStatusEnum status);

    boolean existsByUserId(Long userId);

    @Query("SELECT MAX(q.position) FROM QueueToken q")
    Optional<Integer> findMaxPosition();
}
