package kr.hhplus.be.server.domain.seat;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.TestOnly;

@Entity
@Table(name = "seats")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;

    @Column(nullable = false)
    private Long scheduleId;

    @Column(nullable = false)
    private String row;

    @Column(nullable = false)
    private int seatNumber;

    @Enumerated(EnumType.STRING)
    private SeatStatusEnum seatStatus;

    public Seat(Long scheduleId, String row, int seatNumber, SeatStatusEnum seatStatus) {
        this.scheduleId = scheduleId;
        this.row = row;
        this.seatNumber = seatNumber;
        this.seatStatus = seatStatus;
    }

    @TestOnly
    public Seat(Long seatId, Long scheduleId, String row, int seatNumber, SeatStatusEnum seatStatus) {
        this.seatId = seatId;
        this.scheduleId = scheduleId;
        this.row = row;
        this.seatNumber = seatNumber;
        this.seatStatus = seatStatus;
    }

    public void changeStatus(SeatStatusEnum newStatus) {
        this.seatStatus = newStatus;
    }

    public boolean isAvailableSeat() {
        return seatStatus == SeatStatusEnum.AVAILABLE;
    }
}


