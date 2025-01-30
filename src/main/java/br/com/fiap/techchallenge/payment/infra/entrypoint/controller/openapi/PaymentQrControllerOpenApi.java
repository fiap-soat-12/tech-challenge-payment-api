package br.com.fiap.techchallenge.payment.infra.entrypoint.controller.openapi;

import br.com.fiap.techchallenge.payment.infra.entrypoint.controller.dto.PaymentQrResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@Tag(name = "Qr")
public interface PaymentQrControllerOpenApi {

	@Operation(summary = "Find a Qr By OrderId")
	@ApiResponse(responseCode = "200", description = "Ok Response",
			content = @Content(mediaType = "application/json", schema = @Schema(ref = "PaymentQrResponseDTO")))
	@ApiResponse(responseCode = "404", description = "Not Found Response",
			content = @Content(mediaType = "application/json", schema = @Schema(ref = "ProblemDto")))
	@ApiResponse(responseCode = "500", description = "Internal Server Error Response",
			content = @Content(mediaType = "application/json", schema = @Schema(ref = "ProblemDto")))
	ResponseEntity<PaymentQrResponseDTO> findQrByOrderId(final UUID orderId);

}
