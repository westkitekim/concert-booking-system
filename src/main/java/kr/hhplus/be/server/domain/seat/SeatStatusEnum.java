package kr.hhplus.be.server.domain.seat;

public enum SeatStatusEnum {
    AVAILABLE,   // 예약 가능
    HELD,        // 임시 점유 상태 (e.g. 대기열 통과 후 예약신청)
    RESERVED,    // 예약 완료
    PAID,       // 결제 완료
    BLOCKED      // 예약 불가 좌석 (관리 목적 etc)
}
