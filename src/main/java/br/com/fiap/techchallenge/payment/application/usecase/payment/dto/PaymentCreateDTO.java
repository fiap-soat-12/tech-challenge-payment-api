package br.com.fiap.techchallenge.payment.application.usecase.payment.dto;

import br.com.fiap.techchallenge.payment.infra.entrypoint.consumer.dto.PaymentOrderCreateDTO;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentCreateDTO(UUID orderId, BigDecimal totalAmount) {
	public PaymentCreateDTO(PaymentOrderCreateDTO dto) {
		this(dto.orderId(), dto.amount());
	}
}