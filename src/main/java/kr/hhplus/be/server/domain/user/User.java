package kr.hhplus.be.server.domain.user;

import lombok.Getter;

@Getter
public class User {

    private final long userId;
    private final String username;
    private final String queueToken;

    public User(long userId, String username, String queueToken) {
        this.userId = userId;
        this.username = username;
        this.queueToken = queueToken;
    }

    public static User of(long userId, String username, String queueToken) {
        return new User(userId, username, queueToken);
    }
}
