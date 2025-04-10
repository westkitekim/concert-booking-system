package kr.hhplus.be.server.domain.queuetoken;

import java.util.List;

public interface QueueTokenManager {

    QueueToken issueToken(String username);

    QueueToken findByToken(String token);

    QueueTokenStatusEnum getStatus(String token);

    List<QueueToken> getWaitList();
}
