package br.com.fiap.techchallenge.payment.application.usecase.payment;

import br.com.fiap.techchallenge.payment.domain.models.Payment;

import java.util.UUID;

public interface FindPaymentQrByOrderIdUseCase {

	Payment findQrByOrderId(UUID orderId);

}
