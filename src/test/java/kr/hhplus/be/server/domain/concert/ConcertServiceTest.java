package kr.hhplus.be.server.domain.concert;

import kr.hhplus.be.server.domain.concert.dto.ConcertInfo;
import kr.hhplus.be.server.domain.schedule.Schedule;
import kr.hhplus.be.server.domain.schedule.ScheduleRepository;
import kr.hhplus.be.server.domain.seat.SeatRepository;
import kr.hhplus.be.server.domain.seat.SeatStatusEnum;
import kr.hhplus.be.server.domain.venue.Venue;
import kr.hhplus.be.server.domain.venue.VenueRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ConcertServiceTest {

    @InjectMocks
    private ConcertService concertService;

    @Mock
    private ConcertRepository concertRepository;

    @Mock
    private VenueRepository venueRepository;

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private SeatRepository seatRepository;

    @Test
    @DisplayName("예약 가능한 날짜를 조회한다.")
    void getAvailableDates() {
        Long concertId = 1L;

        Schedule schedule1 = new Schedule(1L, concertId, 100L, LocalDateTime.of(2025, 5, 1, 19, 0));
        Schedule schedule2 = new Schedule(2L, concertId, 100L, LocalDateTime.of(2025, 5, 2, 19, 0));

        given(scheduleRepository.findByConcertId(concertId))
                .willReturn(List.of(schedule1, schedule2));

        given(seatRepository.existsByScheduleIdAndSeatStatus(1L, SeatStatusEnum.AVAILABLE)).willReturn(true);
        given(seatRepository.existsByScheduleIdAndSeatStatus(2L, SeatStatusEnum.AVAILABLE)).willReturn(false);

        Concert concert = new Concert(concertId, "Title");
        Venue venue = new Venue(100L, "Venue", "서울");

        given(concertRepository.findById(concertId)).willReturn(Optional.of(concert));
        given(venueRepository.findById(any())).willReturn(Optional.of(venue));

        List<ConcertInfo> result = concertService.getAvailableDates(concertId);

        assertEquals(1, result.size());
        assertEquals(
                LocalDate.of(2025, 5, 1),
                result.get(0).venueSchedules().get(0).schedules().get(0).startAt()
        );

    }

}

