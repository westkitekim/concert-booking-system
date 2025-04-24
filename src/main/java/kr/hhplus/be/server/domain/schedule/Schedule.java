package kr.hhplus.be.server.domain.schedule;

import kr.hhplus.be.server.domain.seat.Seat;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Schedule {
    private final Long id;
    private final Long concertId;
    private final Long venueId;
    List<Seat> seats;
    private final LocalDateTime startAt;

    public Schedule(Long id, Long concertId, Long venueId, LocalDateTime startAt) {
        this.id = id;
        this.concertId = concertId;
        this.venueId = venueId;
        this.seats = new ArrayList<>();
        this.startAt = startAt;
    }
}

