package kr.hhplus.be.server.domain.concert;

import kr.hhplus.be.server.domain.schedule.ScheduleRepository;
import kr.hhplus.be.server.domain.seat.Seat;
import kr.hhplus.be.server.domain.seat.SeatRepository;
import kr.hhplus.be.server.domain.seat.SeatStatusEnum;
import kr.hhplus.be.server.domain.venue.Venue;
import kr.hhplus.be.server.domain.venue.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ConcertService {

    private final ConcertRepository concertRepository;
    private final VenueRepository venueRepository;
    private final ScheduleRepository scheduleRepository;
    private final SeatRepository seatRepository;

    public List<ConcertInfo> getAvailableDates(Long concertId) {
        return scheduleRepository.findByConcertId(concertId).stream()
                .filter(schedule -> seatRepository.existsByScheduleIdAndSeatStatus(schedule.getId(), SeatStatusEnum.AVAILABLE))
                .map(schedule -> {
                    Concert concert = concertRepository.findById(concertId).orElseThrow();
                    Venue venue = venueRepository.findById(schedule.getVenueId()).orElseThrow();
                    return ConcertInfo.of(concert, schedule, venue, List.of()); // 좌석은 안 넣음
                })
                .toList();
    }

    public List<ConcertInfo> getAvailableSeats(Long concertId) {
        return scheduleRepository.findByConcertId(concertId).stream()
                .map(schedule -> {
                    Concert concert = concertRepository.findById(concertId).orElseThrow();
                    Venue venue = venueRepository.findById(schedule.getVenueId()).orElseThrow();
                    List<Seat> seats = seatRepository.findAllByScheduleId(schedule.getId());
                    return ConcertInfo.of(concert, schedule, venue, seats);
                })
                .toList();
    }

    public List<ConcertInfo> findConcerts(FilterConcertQuery query) {
        return concertRepository.findAll().stream()
                .filter(concert -> query.title() == null || concert.getTitle().contains(query.title()))
                .flatMap(concert -> concert.getSchedules().stream()
                        .filter(schedule -> (query.venueId() == null || schedule.getVenueId().equals(query.venueId())) &&
                                (query.from() == null || !schedule.getStartAt().isBefore(query.from())) &&
                                (query.to() == null || !schedule.getStartAt().isAfter(query.to())))
                        .map(schedule -> ConcertInfo.of(
                                concert,
                                schedule,
                                venueRepository.findById(schedule.getVenueId()).orElseThrow(),
                                seatRepository.findAllByScheduleId(schedule.getId())
                        ))
                )
                .toList();
    }

}

