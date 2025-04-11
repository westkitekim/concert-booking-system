package kr.hhplus.be.server.domain.reservation;

import kr.hhplus.be.server.domain.queuetoken.QueueTokenService;
import kr.hhplus.be.server.domain.seat.Seat;
import kr.hhplus.be.server.domain.seat.SeatRepository;
import kr.hhplus.be.server.domain.seat.SeatStatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final SeatRepository seatRepository;
    private final QueueTokenService queueTokenService;

    private final ConcurrentHashMap<String, ReentrantLock> seatLocks = new ConcurrentHashMap<>();

    /**
     * 좌석 점유 → AVAILABLE → HELD
     */
    public ReservationInfo holdSeat(ReservationCommand command) {
        queueTokenService.validate(command.token(), command.userId());

        Seat seat = _validateSeatIsAvailable(command.scheduleId(), command.seatId());

        seat.changeStatus(SeatStatusEnum.HELD);
        seatRepository.save(seat);

        return ReservationInfo.of(command, seat);
    }

    /**
     *  예약 : HELD → RESERVED
     */
    public ReservationInfo reserveSeat(ReservationCommand command) {
        queueTokenService.validate(command.token(), command.userId());

        if (reservationRepository.existsByScheduleIdAndSeatId(command.scheduleId(), String.valueOf(command.seatId()))) {
            throw new IllegalStateException("이미 예약된 좌석입니다.");
        }

        Seat seat = _validateSeatIsHeld(command.scheduleId(), command.seatId());

        seat.changeStatus(SeatStatusEnum.RESERVED);
        seatRepository.save(seat);

        Reservation reservation = new Reservation();
        reservation.setUserId(command.userId());
        reservation.setScheduleId(command.scheduleId());
        reservation.setSeatId(String.valueOf(command.seatId()));
        reservation.setReservedAt(LocalDateTime.now());

        reservationRepository.save(reservation);

        return ReservationInfo.of(reservation, seat);
    }

    /**
     * 점유 해제 → HELD → AVAILABLE
     * @param scheduleId
     * @param seatId
     */
    public void releaseSeat(Long scheduleId, Long seatId) {
        Seat seat = seatRepository.findByScheduleIdAndSeatId(scheduleId, seatId)
                .orElseThrow(() -> new IllegalArgumentException("좌석이 존재하지 않습니다."));

        if (seat.getSeatStatus() == SeatStatusEnum.HELD) {
            seat.changeStatus(SeatStatusEnum.AVAILABLE);
            seatRepository.save(seat);
        }
    }

    // AVAILABLE 상태 검증 (점유 전용)
    private Seat _validateSeatIsAvailable(Long scheduleId, Long seatId) {
        Seat seat = seatRepository.findByScheduleIdAndSeatId(scheduleId, seatId)
                .orElseThrow(() -> new IllegalArgumentException("좌석이 존재하지 않습니다."));
        if (seat.getSeatStatus() != SeatStatusEnum.AVAILABLE) {
            throw new IllegalStateException("예약 가능한 좌석이 아닙니다.");
        }
        return seat;
    }

    // HELD 상태 검증 (예약 전용)
    private Seat _validateSeatIsHeld(Long scheduleId, Long seatId) {
        Seat seat = seatRepository.findByScheduleIdAndSeatId(scheduleId, seatId)
                .orElseThrow(() -> new IllegalArgumentException("좌석이 존재하지 않습니다."));
        if (seat.getSeatStatus() != SeatStatusEnum.HELD) {
            throw new IllegalStateException("임시 점유되지 않은 좌석은 예약할 수 없습니다.");
        }
        return seat;
    }
}
