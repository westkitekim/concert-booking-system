package kr.hhplus.be.server.application;

import jakarta.transaction.Transactional;
import kr.hhplus.be.server.domain.account.AccountService;
import kr.hhplus.be.server.domain.payment.PaymentCommand;
import kr.hhplus.be.server.domain.payment.PaymentInfo;
import kr.hhplus.be.server.domain.payment.PaymentService;
import kr.hhplus.be.server.domain.queuetoken.QueueTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentFacade {

    private final QueueTokenService queueTokenService;
    private final AccountService accountService;
    private final PaymentService paymentService;

    @Transactional
    public PaymentInfo pay(PaymentCommand command) {

        // 1. 유저 토큰 검증
        queueTokenService.validate(command.token(), command.userId());

        // 2. 계좌 금액 차감
        accountService.use(command.userId(), command.amount());

        // 3. 결제 처리 + 좌석 상태 변경 PAID
        return paymentService.pay(command);
    }
}
