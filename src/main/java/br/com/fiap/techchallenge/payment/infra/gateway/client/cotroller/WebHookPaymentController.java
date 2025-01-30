package br.com.fiap.techchallenge.payment.infra.gateway.client.cotroller;

import br.com.fiap.techchallenge.payment.application.usecase.payment.UpdatePaymentPaidUseCase;
import br.com.fiap.techchallenge.payment.infra.entrypoint.controller.openapi.WebhookPaymentControllerOpenApi;
import br.com.fiap.techchallenge.payment.infra.gateway.client.cotroller.request.WebHookPaymentRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/webhook-payment")
public class WebHookPaymentController implements WebhookPaymentControllerOpenApi {

	private final UpdatePaymentPaidUseCase updatePaymentPaidUseCase;

	public WebHookPaymentController(UpdatePaymentPaidUseCase updatePaymentPaidUseCase) {
		this.updatePaymentPaidUseCase = updatePaymentPaidUseCase;
	}

	@PostMapping
	public ResponseEntity<Void> handleWebhook(@RequestParam("data.id") String dataId, @RequestParam("type") String type,
			@RequestBody WebHookPaymentRequest request) {
		if (dataId.equals(request.data().id()) && type.equals(request.type())) {
			updatePaymentPaidUseCase.updateStatusByPaymentDataId(dataId);
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
