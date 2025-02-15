package br.com.fiap.techchallenge.payment.infra.entrypoint.controller.openapi;

import br.com.fiap.techchallenge.payment.infra.entrypoint.controller.handler.ErrorsValidateData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Webhook Payment")
public interface WebhookPaymentControllerOpenApi {

	@Operation(summary = "Mercado Pago Payment WebHook")
	@ApiResponse(responseCode = "204", description = "No Content Response",
			content = @Content(mediaType = "application/json"))
	@ApiResponse(responseCode = "400", description = "Bad Request Response",
			content = @Content(mediaType = "application/json",
					array = @ArraySchema(schema = @Schema(implementation = ErrorsValidateData.class))))
	@ApiResponse(responseCode = "404", description = "Not Found Response",
			content = @Content(mediaType = "application/json", schema = @Schema(ref = "ProblemDto")))
	@ApiResponse(responseCode = "409", description = "Conflict Response",
			content = @Content(mediaType = "application/json", schema = @Schema(ref = "ProblemDto")))
	@ApiResponse(responseCode = "500", description = "Internal Server Error Response",
			content = @Content(mediaType = "application/json", schema = @Schema(ref = "ProblemDto")))
	ResponseEntity<Void> handleWebhook(@RequestParam("id") List<String> id, @RequestParam("topic") List<String> topic);

}
