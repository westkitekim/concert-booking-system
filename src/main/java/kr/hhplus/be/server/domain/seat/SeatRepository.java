package kr.hhplus.be.server.domain.seat;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository {

    // 특정 스케줄에 대한 모든 좌석 조회
    List<Seat> findAllByScheduleId(Long scheduleId);

    // 특정 스케줄에서 특정 seatId에 해당하는 좌석 조회
    Optional<Seat> findByScheduleIdAndSeatId(Long scheduleId, Long seatId);

    // 좌석 상태 저장 (예약 여부...)
    void save(Seat seat);

    boolean existsByScheduleIdAndSeatStatus(Long scheduleId, SeatStatusEnum seatStatus);
}

