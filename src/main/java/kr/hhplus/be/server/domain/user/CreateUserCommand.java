package kr.hhplus.be.server.domain.user;

public record CreateUserCommand(Long userId, String username, String queueToken) {}

