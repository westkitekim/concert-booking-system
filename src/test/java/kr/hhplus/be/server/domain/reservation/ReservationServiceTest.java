package kr.hhplus.be.server.domain.reservation;

import kr.hhplus.be.server.domain.queuetoken.QueueTokenService;
import kr.hhplus.be.server.domain.seat.Seat;
import kr.hhplus.be.server.domain.seat.SeatRepository;
import kr.hhplus.be.server.domain.seat.SeatStatusEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    ReservationRepository reservationRepository;

    @Mock
    SeatRepository seatRepository;

    @Mock
    QueueTokenService queueTokenService;

    @InjectMocks
    ReservationService reservationService;

    @Test
    void reserveSeat_shouldFail_whenSeatNotHeld() {
        // given
        ReservationCommand command = new ReservationCommand("token", 1L, 10L, 100L, 5000L, BigDecimal.valueOf(5000L)); // 5000L로 명시
        Seat seat = new Seat(100L, 10L, "A", 1, SeatStatusEnum.AVAILABLE); // HELD 아님
        when(reservationRepository.existsByScheduleIdAndSeatId(10L, "100")).thenReturn(false);
        when(seatRepository.findByScheduleIdAndSeatId(10L, 100L)).thenReturn(Optional.of(seat));

        // when & then
        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> reservationService.reserveSeat(command));
        assertEquals("임시 점유되지 않은 좌석은 예약할 수 없습니다.", ex.getMessage());
    }

    @Test
    void reserveSeat_shouldFail_whenAlreadyReserved() {
        // given
        ReservationCommand command = new ReservationCommand("token", 1L, 12L, 102L, 5002L, BigDecimal.valueOf(5000L));
        when(reservationRepository.existsByScheduleIdAndSeatId(10L, "100")).thenReturn(true); // 이미 예약됨

        // when & then
        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> reservationService.reserveSeat(command));
        assertEquals("이미 예약된 좌석입니다.", ex.getMessage());
    }

    @Test
    void releaseSeat_shouldNotChange_ifSeatNotHeld() {
        // given
        Seat seat = new Seat(100L, 10L, "A", 1, SeatStatusEnum.RESERVED); // 해제 불가 상태
        when(seatRepository.findByScheduleIdAndSeatId(10L, 100L)).thenReturn(Optional.of(seat));

        // when
        reservationService.releaseSeat(10L, 100L);

        // then
        assertEquals(SeatStatusEnum.RESERVED, seat.getSeatStatus());
        verify(seatRepository, never()).save(seat);
    }

    @Test
    void reserveSeat_success() {
        // given
        ReservationCommand command = new ReservationCommand("token", 1L, 13L, 123L, 5003L, BigDecimal.valueOf(5000L));
        Seat seat = new Seat(100L, 10L, "A", 1, SeatStatusEnum.HELD);
        when(reservationRepository.existsByScheduleIdAndSeatId(10L, "100")).thenReturn(false);
        when(seatRepository.findByScheduleIdAndSeatId(10L, 100L)).thenReturn(Optional.of(seat));

        // when
        ReservationInfo result = reservationService.reserveSeat(command);

        // then
        assertEquals("RESERVED", result.seatStatus());
        assertEquals(100L, result.seatId());
        verify(seatRepository).save(seat);
        verify(reservationRepository).save(any(Reservation.class));
    }

    @Test
    void releaseSeat_success() {
        // given
        Seat seat = new Seat(100L, 10L, "A", 1, SeatStatusEnum.HELD);
        when(seatRepository.findByScheduleIdAndSeatId(10L, 100L)).thenReturn(Optional.of(seat));

        // when
        reservationService.releaseSeat(10L, 100L);

        // then
        assertEquals(SeatStatusEnum.AVAILABLE, seat.getSeatStatus());
        verify(seatRepository).save(seat);
    }
}
