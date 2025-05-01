package kr.hhplus.be.server.domain.reservation;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationRepository {

    boolean existsByScheduleIdAndSeatId(Long scheduleId, Long seatId);

    Optional<Reservation> findByScheduleIdAndSeatId(Long scheduleId, Long seatId);

    void save(Reservation reservation);
}
