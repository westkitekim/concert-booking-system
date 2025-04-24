package kr.hhplus.be.server.interfaces.payment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.hhplus.be.server.domain.payment.PaymentCommand;
import kr.hhplus.be.server.domain.payment.PaymentInfo;
import kr.hhplus.be.server.domain.payment.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@Tag(name = "Payment", description = "결제 API")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/pay")
    @Operation(summary = "결제 요청", description = "잔액 차감 후 좌석 소유권 이전, 결제 실패 시 임시 점유 해제")
    @ApiResponse(responseCode = "200", description = "결제 성공", content = @Content(schema = @Schema(implementation = PaymentResponse.class)))
    @ApiResponse(responseCode = "400", description = "잔액 부족 또는 유효하지 않은 요청")
    public ResponseEntity<PaymentResponse> pay(@RequestBody @Valid PaymentRequest request) {
        PaymentCommand command = PaymentCommand.of(request);

        // 결제 처리
        PaymentInfo info = paymentService.pay(command);

        // 결과 변환 및 응답
        return ResponseEntity.ok(PaymentResponse.from(info));
    }

}
