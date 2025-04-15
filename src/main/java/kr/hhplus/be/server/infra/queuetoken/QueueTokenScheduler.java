package kr.hhplus.be.server.infra.queuetoken;

import kr.hhplus.be.server.domain.queuetoken.QueueToken;
import kr.hhplus.be.server.domain.queuetoken.QueueTokenRepository;
import kr.hhplus.be.server.domain.queuetoken.QueueTokenStatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class QueueTokenScheduler {

    private final QueueTokenRepository queueTokenRepository;

    @Scheduled(fixedDelay = 2000) // 2초마다 실행
    public void processQueueAndExpireTokens() {
        processTokenExpiry();   // 1. 만료 처리 먼저
        processQueueEntry();    // 2. 입장 처리
    }

    // 토큰 만료 처리
    private void processTokenExpiry() {
        LocalDateTime now = LocalDateTime.now();

        // 대기열 토큰 중 시간 만료된 토큰 상태 갱신
        List<QueueToken> expiredTokens = queueTokenRepository.findExpiredTokens(now);

        for (QueueToken token : expiredTokens) {
            token.changeTokenStatus(QueueTokenStatusEnum.EXPIRED);
            queueTokenRepository.save(token); // 갱신된 상태 저장
        }
    }

    // 대기열 토큰 입장
    private void processQueueEntry() {
        List<QueueToken> waitList = queueTokenRepository.findAllByStatus(QueueTokenStatusEnum.PENDING);

        int maxAdmit = 1; // 입장가능 수
        int admitted = 0;

        for (QueueToken token : waitList) {
            if (admitted >= maxAdmit) break;
            if (!token.isExpiredTime()) {
                token.changeTokenStatus(QueueTokenStatusEnum.READY);
                queueTokenRepository.save(token);
                admitted++;
            }
        }
    }
}

