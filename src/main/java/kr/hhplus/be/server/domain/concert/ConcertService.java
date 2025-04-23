package kr.hhplus.be.server.domain.concert;

import kr.hhplus.be.server.domain.concert.dto.*;
import kr.hhplus.be.server.domain.schedule.Schedule;
import kr.hhplus.be.server.domain.schedule.ScheduleRepository;
import kr.hhplus.be.server.domain.seat.Seat;
import kr.hhplus.be.server.domain.seat.SeatRepository;
import kr.hhplus.be.server.domain.seat.SeatStatusEnum;
import kr.hhplus.be.server.domain.venue.Venue;
import kr.hhplus.be.server.domain.venue.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ConcertService {

    private final ConcertRepository concertRepository;
    private final VenueRepository venueRepository;
    private final ScheduleRepository scheduleRepository;
    private final SeatRepository seatRepository;

    public List<ConcertInfo> getAvailableDates(Long concertId) {
        Concert concert = concertRepository.findById(concertId).orElseThrow();

        Map<Long, VenueScheduleInfo> venueMap = new LinkedHashMap<>();

        List<Schedule> schedules = scheduleRepository.findByConcertId(concertId).stream()
                .filter(schedule -> seatRepository.existsByScheduleIdAndSeatStatus(schedule.getId(), SeatStatusEnum.AVAILABLE))
                .toList();

        for (Schedule schedule : schedules) {
            Venue venue = venueRepository.findById(schedule.getVenueId()).orElseThrow();

            ScheduleSeatInfo scheduleSeatInfo = new ScheduleSeatInfo(
                    schedule.getId(),
                    schedule.getStartAt(),
                    List.of() // 좌석 정보 포함하지 않음
            );

            venueMap.computeIfAbsent(venue.getId(), id -> new VenueScheduleInfo(
                    venue.getId(),
                    venue.getName(),
                    new ArrayList<>()
            )).schedules().add(scheduleSeatInfo);
        }

        return List.of(ConcertInfo.of(concert, new ArrayList<>(venueMap.values())));
    }

    public List<ConcertInfo> getAvailableSeats(Long concertId) {
        Concert concert = concertRepository.findById(concertId).orElseThrow();

        Map<Long, VenueScheduleInfo> venueMap = new LinkedHashMap<>();

        List<Schedule> schedules = scheduleRepository.findByConcertId(concertId);

        for (Schedule schedule : schedules) {
            Venue venue = venueRepository.findById(schedule.getVenueId()).orElseThrow();
            List<SeatInfo> seatInfos = seatRepository.findAllByScheduleId(schedule.getId()).stream()
                    .map(SeatInfo::of)
                    .toList();

            ScheduleSeatInfo scheduleSeatInfo = new ScheduleSeatInfo(
                    schedule.getId(),
                    schedule.getStartAt(),
                    seatInfos
            );

            venueMap.computeIfAbsent(venue.getId(), id -> new VenueScheduleInfo(
                    venue.getId(),
                    venue.getName(),
                    new ArrayList<>()
            )).schedules().add(scheduleSeatInfo);
        }

        return List.of(ConcertInfo.of(concert, new ArrayList<>(venueMap.values())));
    }


//    public List<ConcertInfo> findConcerts(FilterConcertQuery query) {
//        return concertRepository.findAll().stream()
//                .filter(concert -> query.title() == null || concert.getTitle().contains(query.title()))
//                .flatMap(concert -> {
//                    List<Schedule> schedules = scheduleRepository.findByConcertId(concert.getId());
//
//                    return schedules.stream()
//                            .filter(schedule ->
//                                    (query.venueId() == null || schedule.getVenueId().equals(query.venueId())) &&
//                                            (query.from() == null || !schedule.getStartAt().isBefore(query.from())) &&
//                                            (query.to() == null || !schedule.getStartAt().isAfter(query.to())))
//                            .map(schedule -> ConcertInfo.of(
//                                    concert,
//                                    schedule,
//                                    venueRepository.findById(schedule.getVenueId()).orElseThrow(),
//                                    seatRepository.findAllByScheduleId(schedule.getId())
//                            ));
//                })
//                .toList();
//    }

    public List<ConcertInfo> getConcertsWithSchedules(FilterConcertQuery query) {
        List<Concert> concerts = concertRepository.findAll();

        return concerts.stream()
                .filter(c -> query.title() == null || c.getTitle().contains(query.title()))
                .map(concert -> {
                    List<Schedule> schedules = scheduleRepository.findByConcertId(concert.getId()).stream()
                            .filter(s ->
                                    (query.venueId() == null || s.getVenueId().equals(query.venueId())) &&
                                            (query.from() == null || !s.getStartAt().isBefore(query.from())) &&
                                            (query.to() == null || !s.getStartAt().isAfter(query.to()))
                            )
                            .filter(s -> seatRepository.existsByScheduleIdAndSeatStatus(s.getId(), SeatStatusEnum.AVAILABLE))
                            .toList();

                    // VenueScheduleInfo 구성
                    Map<Long, VenueScheduleInfo> venueMap = new LinkedHashMap<>();

                    for (Schedule schedule : schedules) {
                        Venue venue = venueRepository.findById(schedule.getVenueId()).orElseThrow();
                        List<SeatInfo> seatInfos = seatRepository.findAllByScheduleId(schedule.getId())
                                .stream().map(SeatInfo::of).toList();

                        ScheduleSeatInfo scheduleSeatInfo = new ScheduleSeatInfo(
                                schedule.getId(),
                                schedule.getStartAt(),
                                seatInfos
                        );

                        venueMap.computeIfAbsent(venue.getId(), id -> new VenueScheduleInfo(
                                venue.getId(),
                                venue.getName(),
                                new ArrayList<ScheduleSeatInfo>()
                        )).schedules().add(scheduleSeatInfo);
                    }

                    return ConcertInfo.of(concert, new ArrayList<>(venueMap.values()));
                })
                .filter(info -> !info.venueSchedules().isEmpty())
                .toList();
    }
}

