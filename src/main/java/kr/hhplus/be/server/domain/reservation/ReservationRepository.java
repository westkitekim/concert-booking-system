package kr.hhplus.be.server.domain.reservation;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationRepository {

    boolean existsByScheduleIdAndSeatId(Long scheduleId, String seatId);

    Optional<Reservation> findByUserIdAndSeatId(Long userId, String seatId);

    void save(Reservation reservation);
}
