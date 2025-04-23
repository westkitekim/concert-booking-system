package kr.hhplus.be.server.infrastructure.queuetoken;

import kr.hhplus.be.server.domain.queuetoken.QueueToken;
import kr.hhplus.be.server.domain.queuetoken.QueueTokenRepository;
import kr.hhplus.be.server.domain.queuetoken.QueueTokenStatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QueueTokenRepositoryImpl implements QueueTokenRepository {

    private final JpaQueueTokenRepository jpaRepository;

    @Override
    public QueueToken save(QueueToken token) {
        return jpaRepository.save(token);
    }

    @Override
    public Optional<Integer> findMaxPosition() {
        return jpaRepository.findMaxPosition();
    }

    @Override
    public Optional<QueueToken> findByToken(String token) {
        return jpaRepository.findByToken(token);
    }

    @Override
    public List<QueueToken> findAllByStatus(QueueTokenStatusEnum status) {
        return jpaRepository.findAllByTokenStatus(status);
    }

    @Override
    public List<QueueToken> findExpiredTokens(LocalDateTime now) {
        OffsetDateTime cutoff = now.atZone(ZoneId.systemDefault()).toOffsetDateTime();
        return jpaRepository.findAllByExpiresAtBefore(cutoff);
    }

    @Override
    public Optional<QueueToken> findReadyTokenWithMinPosition() {
        return jpaRepository.findTopByTokenStatusOrderByPositionAsc(QueueTokenStatusEnum.READY);
    }

    @Override
    public boolean existsByUserId(Long userId) {
        return jpaRepository.existsByUserId(userId);
    }
}
