package br.com.fiap.techchallenge.payment.infra.entrypoint.controller.dto;

import br.com.fiap.techchallenge.payment.domain.models.Payment;
import io.swagger.v3.oas.annotations.media.Schema;

public record PaymentQrResponseDTO(@Schema(
		example = "00020126580014br.gov.bcb.pix0136123e4567-e12b-12d1-a456-426655440000 5204000053039865802BR5913Fulano de Tal6008BRASILIA62070503***63041D3D") String qr) {
	public PaymentQrResponseDTO(Payment payment) {
		this(payment.getQr());
	}
}