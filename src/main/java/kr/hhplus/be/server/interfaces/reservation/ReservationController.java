package kr.hhplus.be.server.interfaces.reservation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.hhplus.be.server.application.ReservationFacade;
import kr.hhplus.be.server.domain.reservation.ReservationCommand;
import kr.hhplus.be.server.domain.reservation.ReservationInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/reservations")
@Tag(name = "Reservation", description = "예약 API")
public class ReservationController {

    private final ReservationFacade reservationFacade;

    public ReservationController(ReservationFacade reservationFacade) {
        this.reservationFacade = reservationFacade;
    }

    @PostMapping
    @Operation(summary = "좌석 예약", description = "토큰 유효성 검사, 좌석 점유, 결제, 예약 저장까지 전체 예약 흐름 처리")
    @ApiResponse(responseCode = "200", description = "예약 성공", content = @Content(schema = @Schema(implementation = ReservationResponse.class)))
    @ApiResponse(responseCode = "400", description = "유효하지 않은 요청")
    public ResponseEntity<ReservationResponse> reserve(@RequestBody @Valid ReservationRequest request) {
        ReservationCommand command = ReservationCommand.of(request);
        ReservationInfo info = reservationFacade.reserveWithPayment(command);
        return ResponseEntity.ok(ReservationResponse.from(info));
    }
}

