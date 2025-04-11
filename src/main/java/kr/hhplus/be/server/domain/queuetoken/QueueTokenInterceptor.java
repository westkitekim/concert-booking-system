package kr.hhplus.be.server.domain.queuetoken;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class QueueTokenInterceptor implements HandlerInterceptor {

    private final QueueTokenService queueTokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("QUEUE-TOKEN");

        if (token == null || token.isBlank()) {
            throw new InvalidQueueTokenException("토큰이 존재하지 않습니다.");
        }

        QueueTokenStatusEnum status = queueTokenService.getStatus(token);

        if (status != QueueTokenStatusEnum.PENDING) {
            throw new InvalidQueueTokenException("아직 입장 가능한 상태가 아닙니다.");
        }

        return true; // 통과
    }
}

