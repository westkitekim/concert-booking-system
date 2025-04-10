package kr.hhplus.be.server.domain.queuetoken;

import kr.hhplus.be.server.domain.user.User;
import kr.hhplus.be.server.domain.user.UserManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QueueTokenManagerImpl implements QueueTokenManager {

    private final QueueTokenRepository queueTokenRepository;
    private final UserManager userManager;
    private final TokenGenerator tokenGenerator;

    @Override
    public QueueToken issueToken(String username) {
        // 1. 최초 대기열 진입시 유저 생성
        User user = userManager.create(username, "");

        // 2. 현재 대기열에서 가장 큰 순번 + 1 → 새로운 position
        int lastPosition = queueTokenRepository.findMaxPosition().orElse(0);
        int nextPosition = lastPosition + 1;

        // 3. 토큰 생성 및 저장
        String tokenVal = tokenGenerator.generateToken(user.getUserId());
        QueueToken token = new QueueToken(user, tokenVal, nextPosition);
        queueTokenRepository.save(token);
        return token;
    }

    @Override
    public QueueToken findByToken(String token) {
        return queueTokenRepository.findByToken(token);
    }

    @Override
    public QueueTokenStatusEnum getStatus(String token) {
        QueueToken queueToken = queueTokenRepository.findByToken(token);
        if (queueToken == null || queueToken.isExpiredTime()) {
            return QueueTokenStatusEnum.EXPIRED;
        }
        return queueToken.getTokenStatus();
    }

    @Override
    public List<QueueToken> getWaitList() {
        return queueTokenRepository.findAllByStatus(QueueTokenStatusEnum.PENDING);
    }
}
