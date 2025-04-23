package kr.hhplus.be.server.infrastructure.seat;

import kr.hhplus.be.server.domain.seat.Seat;
import kr.hhplus.be.server.domain.seat.SeatRepository;
import kr.hhplus.be.server.domain.seat.SeatStatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SeatRepositoryImpl implements SeatRepository {

    private final JpaSeatRepository jpaSeatJpaRepository;

    @Override
    public List<Seat> findAllByScheduleId(Long scheduleId) {
        return jpaSeatJpaRepository.findAllByScheduleId(scheduleId);
    }

    @Override
    public Optional<Seat> findByScheduleIdAndSeatId(Long scheduleId, Long seatId) {
        return jpaSeatJpaRepository.findByScheduleIdAndSeatId(scheduleId, seatId);
    }

    @Override
    public void save(Seat seat) {
        jpaSeatJpaRepository.save(seat);
    }

    @Override
    public boolean existsByScheduleIdAndSeatStatus(Long scheduleId, SeatStatusEnum seatStatus) {
        return jpaSeatJpaRepository.existsByScheduleIdAndSeatStatus(scheduleId, seatStatus);
    }
}

