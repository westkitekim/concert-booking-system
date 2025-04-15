package kr.hhplus.be.server.application;

import kr.hhplus.be.server.domain.account.AccountService;
import kr.hhplus.be.server.domain.payment.PaymentService;
import kr.hhplus.be.server.domain.queuetoken.QueueTokenService;
import kr.hhplus.be.server.domain.reservation.ReservationCommand;
import kr.hhplus.be.server.domain.reservation.ReservationInfo;
import kr.hhplus.be.server.domain.reservation.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ReservationFacade {

    private final QueueTokenService queueTokenService;
    private final ReservationService reservationService;
    private final AccountService accountService;
    private final PaymentService paymentService;

    @Transactional
    public ReservationInfo reserveWithPayment(ReservationCommand command) {

        // 1. 유효 토큰 검증
        queueTokenService.validate(command.token(), command.userId());

        // 2. 좌석 점유 (HELD 상태)
        ReservationInfo holdInfo = reservationService.holdSeat(command);

        // 3. 금액 차감
        accountService.use(command.userId(), command.amount());

        return holdInfo;
    }
}
