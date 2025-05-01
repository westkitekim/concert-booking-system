package kr.hhplus.be.server.infrastructure.schedule;

import kr.hhplus.be.server.domain.schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByConcertId(Long concertId);

    List<Schedule> findByVenueId(Long venueId);
}
