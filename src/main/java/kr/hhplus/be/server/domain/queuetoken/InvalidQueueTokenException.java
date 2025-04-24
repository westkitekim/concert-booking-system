package kr.hhplus.be.server.domain.queuetoken;

public class InvalidQueueTokenException extends RuntimeException {
    public InvalidQueueTokenException(String message) {
        super(message);
    }
}

