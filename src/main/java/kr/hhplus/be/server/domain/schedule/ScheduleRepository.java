package kr.hhplus.be.server.domain.schedule;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository {

    Optional<Schedule> findById(Long scheduleId);

    List<Schedule> findAll();

    List<Schedule> findByConcertId(Long concertId);

    List<Schedule> findByVenueId(Long venueId);
}
