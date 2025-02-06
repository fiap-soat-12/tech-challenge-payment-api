package br.com.fiap.techchallenge.payment.infra.gateway.producer.dto;

import java.util.UUID;

public record OrderStatusUpdateDTO(UUID orderId, boolean isPaid) {
}