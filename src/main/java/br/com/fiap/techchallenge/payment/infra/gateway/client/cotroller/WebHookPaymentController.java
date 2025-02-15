package br.com.fiap.techchallenge.payment.infra.gateway.client.cotroller;

import br.com.fiap.techchallenge.payment.application.usecase.payment.UpdatePaymentPaidUseCase;
import br.com.fiap.techchallenge.payment.infra.entrypoint.controller.openapi.WebhookPaymentControllerOpenApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/v1/webhook-payment")
public class WebHookPaymentController implements WebhookPaymentControllerOpenApi {

	private static final Logger log = LoggerFactory.getLogger(WebHookPaymentController.class);

	private final UpdatePaymentPaidUseCase updatePaymentPaidUseCase;

	public WebHookPaymentController(UpdatePaymentPaidUseCase updatePaymentPaidUseCase) {
		this.updatePaymentPaidUseCase = updatePaymentPaidUseCase;
	}

	@PostMapping
	public ResponseEntity<Void> handleWebhook(@RequestParam("id") List<String> id,
			@RequestParam("topic") List<String> topic) {
		log.info("Recebendo webhook com ids={} e topics={}", id, topic);

		updatePaymentPaidUseCase.updatePaymentByDataId(id.getFirst());

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
