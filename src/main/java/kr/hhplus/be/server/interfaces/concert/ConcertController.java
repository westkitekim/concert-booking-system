package kr.hhplus.be.server.interfaces.concert;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.domain.concert.ConcertInfo;
import kr.hhplus.be.server.domain.concert.ConcertService;
import kr.hhplus.be.server.domain.concert.SeatInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/concerts")
@Tag(name = "Concert", description = "콘서트 예약 조회 API")
class ConcertController {

    private final ConcertService concertService;

    @GetMapping("/{concertId}/available-dates")
    @Operation(summary = "예약 가능 날짜 조회", description = "예약 가능한 날짜 조회")
    public ResponseEntity<List<ConcertInfo>> getAvailableDates(@PathVariable Long concertId) {
        if (concertId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.emptyList());
        }

        List<ConcertInfo> availableDates = concertService.getAvailableDates(concertId);
        return ResponseEntity.ok(availableDates);
    }

    @GetMapping("/{concertId}/available-seats")
    @Operation(summary = "예약 가능 좌석 조회", description = "예약 가능한 좌석 목록 반환")
    public ResponseEntity<List<ConcertInfo>> getAvailableSeats(@PathVariable Long concertId) {
        if (concertId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.emptyList());
        }

        List<ConcertInfo> availableSeats = concertService.getAvailableSeats(concertId);
        return ResponseEntity.ok(availableSeats);
    }
}
