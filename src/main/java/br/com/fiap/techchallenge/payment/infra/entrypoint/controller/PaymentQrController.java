package br.com.fiap.techchallenge.payment.infra.entrypoint.controller;

import br.com.fiap.techchallenge.payment.application.usecase.payment.FindPaymentQrByOrderIdUseCase;
import br.com.fiap.techchallenge.payment.infra.entrypoint.controller.dto.PaymentQrResponseDTO;
import br.com.fiap.techchallenge.payment.infra.entrypoint.controller.openapi.PaymentQrControllerOpenApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/qrs")
public class PaymentQrController implements PaymentQrControllerOpenApi {

	private final FindPaymentQrByOrderIdUseCase findPaymentQrByOrderIdUseCase;

	public PaymentQrController(FindPaymentQrByOrderIdUseCase findPaymentQrByOrderIdUseCase) {
		this.findPaymentQrByOrderIdUseCase = findPaymentQrByOrderIdUseCase;
	}

	@Override
	@GetMapping("/{orderId}")
	public ResponseEntity<PaymentQrResponseDTO> findQrByOrderId(@PathVariable("orderId") final UUID orderId) {
		var pageablePayment = findPaymentQrByOrderIdUseCase.findQrByOrderId(orderId);
		return ResponseEntity.status(HttpStatus.OK).body(new PaymentQrResponseDTO(pageablePayment));
	}

}
