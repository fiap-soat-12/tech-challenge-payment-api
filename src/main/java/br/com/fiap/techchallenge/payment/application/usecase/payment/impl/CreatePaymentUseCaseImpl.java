package br.com.fiap.techchallenge.payment.application.usecase.payment.impl;

import br.com.fiap.techchallenge.payment.application.gateway.client.PaymentClient;
import br.com.fiap.techchallenge.payment.application.persistence.PaymentPersistence;
import br.com.fiap.techchallenge.payment.application.usecase.payment.CreatePaymentUseCase;
import br.com.fiap.techchallenge.payment.application.usecase.payment.dto.PaymentCreateDTO;
import br.com.fiap.techchallenge.payment.domain.models.Payment;
import br.com.fiap.techchallenge.payment.infra.gateway.client.cotroller.dto.PaymentClientDTO;
import br.com.fiap.techchallenge.payment.infra.gateway.client.cotroller.dto.PaymentItemClientDTO;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Transactional
public class CreatePaymentUseCaseImpl implements CreatePaymentUseCase {

	private final PaymentPersistence persistence;

	private final PaymentClient paymentClient;

	public CreatePaymentUseCaseImpl(PaymentPersistence persistence, PaymentClient paymentClient) {
		this.persistence = persistence;
		this.paymentClient = paymentClient;
	}

	@Override
	public Payment create(PaymentCreateDTO input) {
		var externalPaymentId = UUID.randomUUID();
		var qrCode = paymentClient.generateQrCode(createPayment(input.totalAmount(), externalPaymentId));
		var payment = Payment.create(input.totalAmount(), externalPaymentId, qrCode, input.orderId());
		return persistence.create(payment);
	}

	private PaymentClientDTO createPayment(BigDecimal totalAmount, UUID externalPaymentId) {
		return new PaymentClientDTO(List.of(new PaymentItemClientDTO(totalAmount)), totalAmount, externalPaymentId);
	}

}
