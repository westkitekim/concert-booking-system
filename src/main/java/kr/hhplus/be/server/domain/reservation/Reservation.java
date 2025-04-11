package kr.hhplus.be.server.domain.reservation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class Reservation {
    private Long id;
    private Long userId;
    private Long scheduleId;
    private String seatId;
    private LocalDateTime reservedAt;
}
