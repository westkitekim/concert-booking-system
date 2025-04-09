package kr.hhplus.be.server.interfaces.payment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/payments")
@Tag(name = "Payment", description = "결제 API")
public class PaymentController {

    private static final Map<String, Integer> userBalance = new HashMap<>();
    private static final Set<String> ownedSeats = new HashSet<>();

    @PostMapping("/pay")
    @Operation(summary = "결제 요청", description = "잔액 차감 후 좌석 소유권 이전, 결제 실패 시 임시 점유 해제")
    @ApiResponse(responseCode = "200", description = "결제 성공", content = @Content(schema = @Schema(implementation = PaymentResponse.class)))
    @ApiResponse(responseCode = "400", description = "잔액 부족 또는 유효하지 않은 요청")
    public ResponseEntity<?> pay(@RequestBody @Valid PaymentRequest request) {
        int balance = userBalance.getOrDefault(request.userId(), 10000);
        String seatKey = request.date().toString() + ":" + request.seatNumber();

        if (request.amount() > balance) {
            return ResponseEntity.badRequest().body("잔액이 부족합니다. 임시 점유 좌석은 해제됩니다.");
        }

        // 결제 처리: 잔액 차감
        balance -= request.amount();
        userBalance.put(request.userId(), balance);

        // 결제 완료: 소유권 이전
        ownedSeats.add(seatKey);

        return ResponseEntity.ok(new PaymentResponse("success", request.userId(), request.seatNumber()));
    }
}
