package kr.hhplus.be.server.domain.queuetoken;

import kr.hhplus.be.server.domain.user.User;
import kr.hhplus.be.server.domain.user.UserManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QueueTokenService {

    private final QueueTokenRepository queueTokenRepository;
    private final UserManager userManager;
    private final TokenGenerator tokenGenerator;

    // 토큰 검증
    public void validate(String token, Long userId) {
        QueueToken queueToken = queueTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 토큰입니다."));

        queueToken.validateNotExpired();
        queueToken.validateOwner(userId);
        queueToken.validateReady();
    }


    public QueueToken issueToken(String username) {
        // 1. 최초 대기열 진입시 유저 생성
        User user = userManager.create(username, "");

        // 2. 현재 대기열에서 가장 큰 순번 + 1 → 새로운 position
        int lastPosition = queueTokenRepository.findMaxPosition().orElse(0);
        int nextPosition = lastPosition + 1;

        // 3. 토큰 생성 및 저장
        String tokenVal = tokenGenerator.generateToken(user.getUserId());
        QueueToken token = new QueueToken(user.getUserId(), tokenVal, nextPosition);
        queueTokenRepository.save(token);
        return token;
    }

    public Optional<QueueToken> findByToken(String token) {
        return queueTokenRepository.findByToken(token);
    }

    public void changeStatus(String token, QueueTokenStatusEnum newStatus) {
        QueueToken queueToken = queueTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("토큰이 존재하지 않습니다."));

        queueToken.changeTokenStatus(newStatus);
        queueTokenRepository.save(queueToken);
    }

    public QueueTokenStatusEnum getStatus(String token) {
//        QueueToken queueToken = queueTokenRepository.findByToken(token);
//        if (queueToken == null || queueToken.isExpiredTime()) {
//            return QueueTokenStatusEnum.EXPIRED;
//        }
//        return queueToken.getTokenStatus();

        // 함수형
        return queueTokenRepository.findByToken(token)
                .filter(t -> !t.isExpiredTime())
                .map(QueueToken::getTokenStatus)
                .orElse(QueueTokenStatusEnum.EXPIRED);
    }

    public List<QueueToken> getWaitList() {
        return queueTokenRepository.findAllByStatus(QueueTokenStatusEnum.PENDING);
    }
}
