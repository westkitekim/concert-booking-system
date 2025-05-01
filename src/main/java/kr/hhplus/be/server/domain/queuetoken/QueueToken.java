package kr.hhplus.be.server.domain.queuetoken;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.TestOnly;

import java.time.OffsetDateTime;

@Entity
@Table(name = "queue_token")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QueueToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private int position;

    @Enumerated(EnumType.STRING)
    private QueueTokenStatusEnum tokenStatus;

    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private OffsetDateTime expiresAt;

    public QueueToken(Long userId, String token, int position) {
        this.userId = userId;
        this.token = token;
        this.position = position;
        this.tokenStatus = QueueTokenStatusEnum.PENDING;
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = this.createdAt;
        this.expiresAt = this.createdAt.plusMinutes(10);
    }

    @TestOnly
    public QueueToken(Long id, Long userId, String token, int position) {
        this.id = id;
        this.userId = userId;
        this.token = token;
        this.position = position;
        this.tokenStatus = QueueTokenStatusEnum.PENDING;
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = this.createdAt;
        this.expiresAt = this.createdAt.plusMinutes(10);
    }

    @TestOnly
    public QueueToken(Long userId, String token, int position, OffsetDateTime expiresAt) {
        this.userId = userId;
        this.token = token;
        this.position = position;
        this.tokenStatus = QueueTokenStatusEnum.PENDING;
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = this.createdAt;
        this.expiresAt = expiresAt; // 여기만 외부로부터 주입받음
    }

    public boolean isExpiredTime() {
        return OffsetDateTime.now().isAfter(expiresAt);
    }

    public void expireToken() {
        this.tokenStatus = QueueTokenStatusEnum.EXPIRED;
        this.updatedAt = OffsetDateTime.now();
        this.expiresAt = OffsetDateTime.now();
    }

    public void changeTokenStatus(QueueTokenStatusEnum newStatus) {
        if (this.tokenStatus == newStatus) return;
        this.tokenStatus = newStatus;
        this.updatedAt = OffsetDateTime.now();
    }

    public void validateOwner(Long requestUserId) {
        if (!this.userId.equals(requestUserId)) {
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
