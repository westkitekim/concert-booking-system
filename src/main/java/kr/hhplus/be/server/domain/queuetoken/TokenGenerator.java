package kr.hhplus.be.server.domain.queuetoken;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class TokenGenerator {

    public String generateToken(long userId) {

        // 토큰 = UUID + userId
        String combinedData = userId + "";
        String token = UUID.nameUUIDFromBytes(combinedData.getBytes()).toString();

        log.info("generateToken ::: " + token + ", userId ::: " + userId);

        return token;
    }
}
