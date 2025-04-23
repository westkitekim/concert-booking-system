package kr.hhplus.be.server.infrastructure.reservation;

import kr.hhplus.be.server.domain.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findByScheduleIdAndSeatId(Long scheduleId, Long seatId);

    boolean existsByScheduleIdAndSeatId(Long scheduleId, Long seatId);
}
