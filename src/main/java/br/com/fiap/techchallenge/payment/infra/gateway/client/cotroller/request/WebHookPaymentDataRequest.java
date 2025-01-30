package br.com.fiap.techchallenge.payment.infra.gateway.client.cotroller.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record WebHookPaymentDataRequest(@Schema(example = "87847869771") String id) {
}