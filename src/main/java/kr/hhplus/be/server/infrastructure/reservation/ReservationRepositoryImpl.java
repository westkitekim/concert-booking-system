package kr.hhplus.be.server.infrastructure.reservation;

import kr.hhplus.be.server.domain.reservation.Reservation;
import kr.hhplus.be.server.domain.reservation.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {
    private final JpaReservationRepository jpaReservationRepository;

    @Override
    public void save(Reservation reservation) {
        jpaReservationRepository.save(reservation);
    }

    @Override
    public Optional<Reservation> findByScheduleIdAndSeatId(Long scheduleId, Long seatId) {
        return jpaReservationRepository.findByScheduleIdAndSeatId(scheduleId, seatId);
    }

    @Override
    public boolean existsByScheduleIdAndSeatId(Long scheduleId, Long seatId) {
        return jpaReservationRepository.existsByScheduleIdAndSeatId(scheduleId, seatId);
    }
}
