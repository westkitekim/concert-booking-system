package kr.hhplus.be.server.domain.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.TestOnly;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String queueToken;

    public User(String username, String queueToken) {
        this.username = username;
        this.queueToken = queueToken;
    }

    @TestOnly
    public User(Long userId, String username, String queueToken) {
        this.username = username;
        this.queueToken = queueToken;
    }

    public static User of(String username, String queueToken) {
        return new User(username, queueToken);
    }
}
