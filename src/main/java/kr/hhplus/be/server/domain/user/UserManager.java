package kr.hhplus.be.server.domain.user;

public interface UserManager {
    User create(String username, String queueToken);
    User getUserById(Long userId);
}
