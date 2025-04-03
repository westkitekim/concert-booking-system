package kr.hhplus.be.server.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.api.controller.dto.response.SeatInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/concerts")
@Tag(name = "Concert", description = "콘서트 예약 조회 API")
class ConcertController {

    @GetMapping("/{concertId}/available-dates")
    @Operation(summary = "예약 가능 날짜 조회", description = "예약 가능한 날짜 조회")
    public ResponseEntity<List<LocalDate>> getAvailableDates(@PathVariable String concertId) {

        if (concertId == null || concertId.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(List.of());
        }
        return ResponseEntity.ok(List.of(LocalDate.now(), LocalDate.now().plusDays(1)));
    }

    @GetMapping("/{concertId}/available-seats")
    @Operation(summary = "예약 가능 좌석 조회", description = "예약 가능한 좌석 목록 반환")
    public ResponseEntity<List<SeatInfo>> getAvailableSeats(@PathVariable String concertId) {

        if (concertId == null || concertId.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(List.of());
        }
        return ResponseEntity.ok(List.of(
                new SeatInfo("1", true, null),
                new SeatInfo("2", false, "user123")
        ));
    }
}
