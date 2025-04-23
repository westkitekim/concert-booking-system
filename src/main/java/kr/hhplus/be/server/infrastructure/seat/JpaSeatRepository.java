package kr.hhplus.be.server.infrastructure.seat;

import kr.hhplus.be.server.domain.seat.Seat;
import kr.hhplus.be.server.domain.seat.SeatStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaSeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findAllByScheduleId(Long scheduleId);

    Optional<Seat> findByScheduleIdAndSeatId(Long scheduleId, Long seatId);

    boolean existsByScheduleIdAndSeatStatus(Long scheduleId, SeatStatusEnum seatStatus);
}

