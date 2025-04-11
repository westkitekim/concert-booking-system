package kr.hhplus.be.server.domain.account;

import java.math.BigDecimal;

public record ChargeAccountCommand(Long userId, BigDecimal amount) {}

