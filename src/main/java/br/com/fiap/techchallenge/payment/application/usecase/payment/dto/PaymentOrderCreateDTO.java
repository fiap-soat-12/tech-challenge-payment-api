package br.com.fiap.techchallenge.payment.application.usecase.payment.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentOrderCreateDTO(UUID orderId, BigDecimal totalAmount) {
}