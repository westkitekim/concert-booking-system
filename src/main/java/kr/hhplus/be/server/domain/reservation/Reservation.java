package kr.hhplus.be.server.domain.reservation;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservation")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long concertId;  // 🔥 추가 필요

    @Column(nullable = false)
    private Long scheduleId;

    @Column(nullable = false)
    private String seatId;

    @Column(nullable = false)
    private LocalDateTime reservedAt;

    public Reservation(Long userId, Long concertId, Long scheduleId, String seatId, LocalDateTime reservedAt) {
        this.userId = userId;
        this.concertId = concertId;
        this.scheduleId = scheduleId;
        this.seatId = seatId;
        this.reservedAt = reservedAt;
    }

    public static Reservation of(Long userId, Long concertId, Long scheduleId, String seatId) {
        Reservation reservation = new Reservation();
        reservation.userId = userId;
        reservation.concertId = concertId;
        reservation.scheduleId = scheduleId;
        reservation.seatId = seatId;
        reservation.reservedAt = LocalDateTime.now();
        return reservation;
    }
}
