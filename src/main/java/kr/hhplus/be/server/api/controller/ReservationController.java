package kr.hhplus.be.server.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.hhplus.be.server.api.controller.dto.request.ReservationRequest;
import kr.hhplus.be.server.api.controller.dto.response.ReservationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/reservations")
@Tag(name = "Reservation", description = "예약 API")
public class ReservationController {

    private static final Set<String> reservedSeats = new HashSet<>();

    @PostMapping
    @Operation(summary = "좌석 임시 점유", description = "예약 좌석 임시 점유")
    @ApiResponse(responseCode = "200", description = "예약 성공", content = @Content(schema = @Schema(implementation = ReservationResponse.class)))
    @ApiResponse(responseCode = "409", description = "좌석 이미 점유됨")
    public ResponseEntity<?> reserve(@RequestBody @Valid ReservationRequest request) {
        String key = request.date().toString() + ":" + request.seatNumber();
        if (reservedSeats.contains(key)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 점유된 좌석입니다.");
        }
        reservedSeats.add(key);
        return ResponseEntity.ok(new ReservationResponse("reserved", 5));
    }
}
