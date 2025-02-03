package br.com.fiap.techchallenge.payment.infra.entrypoint.consumer.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentOrderCreateDTO(UUID orderId, BigDecimal amount) {
}