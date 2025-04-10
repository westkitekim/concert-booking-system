package kr.hhplus.be.server.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserManagerImpl implements UserManager {

    private final UserRepository userRepository;

    @Override
    public User create(String username, String queueToken) {
        Long lastUserId = userRepository.findLastUserId()
                .map(User::getUserId)
                .orElse(0L);

        long newUserId = lastUserId + 1;
        User user = User.of(newUserId, username, queueToken);
        return userRepository.save(user);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);  // 유저가 없으면 null 반환
    }
}
