package kr.hhplus.be.server.domain.queuetoken;

import kr.hhplus.be.server.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;

@Getter
@Setter
public class QueueToken {

    private Long id;
    private User user;
    private String token;
    private int position;
    private QueueTokenStatusEnum tokenStatus;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private ZonedDateTime expiresAt;

    public QueueToken(User user, String token, int position) {
        this.user = user;
        this.token = token;
        this.position = position;
        this.tokenStatus = QueueTokenStatusEnum.PENDING;
        this.createdAt = ZonedDateTime.now();
        this.updatedAt = this.createdAt;
        this.expiresAt = createdAt.plusMinutes(10);
    }

    public boolean isExpiredTime() {
        return ZonedDateTime.now().isAfter(expiresAt);
    }

    public void expireToken() {
        this.setTokenStatus(QueueTokenStatusEnum.EXPIRED);
        this.setUpdatedAt(ZonedDateTime.now());
        this.setExpiresAt(ZonedDateTime.now());
    }

    public void changeTokenStatus(QueueTokenStatusEnum newStatus) {
        if (this.tokenStatus == newStatus) return;
        this.tokenStatus = newStatus;
        this.updatedAt = ZonedDateTime.now();
    }

    public void validateOwner(Long userId) {
        if (!this.getUser().getUserId().equals(userId)) {
            throw new IllegalStateException("토큰이 사용자와 일치하지 않습니다.");
        }
    }

    public void validateNotExpired() {
        if (this.isExpiredTime()) {
            throw new IllegalStateException("토큰이 만료되었습니다.");
        }
    }

    public void validateReady() {
        if (this.tokenStatus != QueueTokenStatusEnum.READY) {
            throw new IllegalStateException("입장 가능한 상태가 아닙니다.");
        }
    }
}
