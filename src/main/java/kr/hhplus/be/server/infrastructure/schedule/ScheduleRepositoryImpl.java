package kr.hhplus.be.server.infrastructure.schedule;

import kr.hhplus.be.server.domain.schedule.Schedule;
import kr.hhplus.be.server.domain.schedule.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ScheduleRepositoryImpl implements ScheduleRepository {

    private final JpaScheduleRepository jpaScheduleRepository;

    @Override
    public Optional<Schedule> findById(Long id) {
        return jpaScheduleRepository.findById(id);
    }

    @Override
    public List<Schedule> findAll() {
        return jpaScheduleRepository.findAll();
    }

    @Override
    public List<Schedule> findByConcertId(Long concertId) {
        return jpaScheduleRepository.findByConcertId(concertId);
    }

    @Override
    public List<Schedule> findByVenueId(Long venueId) {
        return jpaScheduleRepository.findByVenueId(venueId);
    }
}
