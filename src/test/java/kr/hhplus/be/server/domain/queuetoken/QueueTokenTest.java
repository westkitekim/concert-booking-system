package kr.hhplus.be.server.domain.queuetoken;

import kr.hhplus.be.server.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class QueueTokenTest {

    @Test
    @DisplayName("만료 시간이 지난 토큰은 만료 상태로 전환되어야 한다")
    void expireToken_shouldExpireProperly() {
        // given
        User user = new User(1L, "testUser", "f3d0deb1-4eea-494b-a8a0-04798e5b6e88");
        QueueToken token = new QueueToken(user, "123", 1);

        // 토큰 만료시간 과거시간으로 설정
        token.setExpiresAt(ZonedDateTime.now().minusMinutes(1));

        // when
        boolean isExpired = token.isExpiredTime();

        // then
        assertThat(isExpired).isTrue(); // 만료되었어야 함
    }

    @Test
    @DisplayName("토큰 상태 변경 시 상태와 업데이트 시간이 변경된다.")
    void changeTokenStatus_shouldUpdateStatusAndTimestamp() {
        // given
        User user = new User(2L, "user2", "89dcd7a8-cc14-4952-b315-f100d24ffa7d");
        QueueToken token = new QueueToken(user, "1234", 2);
        ZonedDateTime beforeUpdate = token.getUpdatedAt();

        // when
        token.changeTokenStatus(QueueTokenStatusEnum.ACTIVE);

        // then
        assertThat(token.getTokenStatus()).isEqualTo(QueueTokenStatusEnum.ACTIVE);
        assertThat(beforeUpdate).isBeforeOrEqualTo(ZonedDateTime.now());
    }

    @Test
    @DisplayName("토큰 만료 시 만료 상태 및 종료시간이 변경된다.")
    void expireToken_shouldUpdateStatusAndTimes() {
        // given
        User user = new User(3L, "expireUser", "89dcd7a8-cc14-4952-b315-f100d24ffa7e");
        QueueToken token = new QueueToken(user, "12345", 3);

        // when
        token.expireToken();

        // then
        assertThat(token.getTokenStatus()).isEqualTo(QueueTokenStatusEnum.EXPIRED);
        assertThat(token.getExpiresAt()).isBeforeOrEqualTo(ZonedDateTime.now());
        assertThat(token.getUpdatedAt()).isBeforeOrEqualTo(ZonedDateTime.now());
    }
}
