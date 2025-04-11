package kr.hhplus.be.server.domain.seat;

import lombok.Getter;

@Getter
public class Seat {
    private final Long seatId;
    private final Long scheduleId;
    private final String row;
    private final int seatNumber;
    private SeatStatusEnum seatStatus;

    public Seat(Long seatId, Long scheduleId, String row, int number, SeatStatusEnum isReserved) {
        this.seatId = seatId;
        this.scheduleId = scheduleId;
        this.row = row;
        this.seatNumber = number;
        this.seatStatus = isReserved;
    }

    public void changeStatus(SeatStatusEnum newStatus) {
        this.seatStatus = newStatus;
    }

    public boolean isAvailableSeat() {
        return seatStatus == SeatStatusEnum.AVAILABLE;
    }
}


