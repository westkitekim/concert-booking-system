package kr.hhplus.be.server.domain.payment;

import kr.hhplus.be.server.domain.seat.Seat;
import kr.hhplus.be.server.domain.seat.SeatRepository;
import kr.hhplus.be.server.domain.seat.SeatStatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final SeatRepository seatRepository;
    private final PaymentRepository paymentRepository;

    public PaymentInfo pay(PaymentCommand command) {
        Seat seat = seatRepository.findByScheduleIdAndSeatId(command.scheduleId(), command.seatId())
                .orElseThrow(() -> new IllegalArgumentException("좌석이 존재하지 않습니다."));

        seat.changeStatus(SeatStatusEnum.PAID);
        seatRepository.save(seat);

        Payment payment = Payment.of(
                command.userId(),
                command.concertId(),
                command.scheduleId(),
                command.seatId(),
                command.amount()
        );
        paymentRepository.save(payment);

        return PaymentInfo.from(payment);
    }
}

