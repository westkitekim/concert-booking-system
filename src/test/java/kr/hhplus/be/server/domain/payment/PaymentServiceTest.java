package kr.hhplus.be.server.domain.payment;

import kr.hhplus.be.server.domain.seat.Seat;
import kr.hhplus.be.server.domain.seat.SeatRepository;
import kr.hhplus.be.server.domain.seat.SeatStatusEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    SeatRepository seatRepository;

    @Mock
    PaymentRepository paymentRepository;

    @InjectMocks
    PaymentService paymentService;

    @Test
    @DisplayName("정상 결제 시 Seat 상태 변경 및 결제내역 저장")
    void pay_shouldSucceed_whenValidRequest() {

        // given
        PaymentCommand command = new PaymentCommand("AUKDJFK-113", 1L, 100L, 10L, 55L, new BigDecimal("30000"));
        Seat mockSeat = new Seat(55L, "A", 1, SeatStatusEnum.HELD);
        Payment mockPayment = Payment.of(1L, 100L, 10L, 55L, new BigDecimal("30000"));

        when(seatRepository.findByScheduleIdAndSeatId(10L, 55L)).thenReturn(Optional.of(mockSeat));
        when(paymentRepository.save(any())).thenReturn(mockPayment);

        // when
        PaymentInfo result = paymentService.pay(command);

        // then
        assertEquals(SeatStatusEnum.PAID, mockSeat.getSeatStatus());
        assertEquals(1L, result.userId());
        assertEquals(new BigDecimal("30000"), result.amount());
        verify(seatRepository).save(mockSeat);
        verify(paymentRepository).save(any(Payment.class));
    }

    @Test
    void pay_shouldFail_whenSeatNotFound() {
        // given
        PaymentCommand command = new PaymentCommand("AUKDJFK-113", 1L, 100L, 10L, 99L, new BigDecimal("30000"));
        when(seatRepository.findByScheduleIdAndSeatId(10L, 99L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(IllegalArgumentException.class, () -> paymentService.pay(command));
        verify(paymentRepository, never()).save(any());
    }
}
