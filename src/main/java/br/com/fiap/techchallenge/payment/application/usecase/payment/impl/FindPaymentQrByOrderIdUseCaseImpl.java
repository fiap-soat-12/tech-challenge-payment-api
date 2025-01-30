package br.com.fiap.techchallenge.payment.application.usecase.payment.impl;

import br.com.fiap.techchallenge.payment.application.exceptions.DoesNotExistException;
import br.com.fiap.techchallenge.payment.application.persistence.PaymentPersistence;
import br.com.fiap.techchallenge.payment.application.usecase.payment.FindPaymentQrByOrderIdUseCase;
import br.com.fiap.techchallenge.payment.domain.models.Payment;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional
public class FindPaymentQrByOrderIdUseCaseImpl implements FindPaymentQrByOrderIdUseCase {

	private final PaymentPersistence persistence;

	public FindPaymentQrByOrderIdUseCaseImpl(PaymentPersistence persistence) {
		this.persistence = persistence;
	}

	@Override
	public Payment findQrByOrderId(UUID orderId) {
		return persistence.findByOrderId(orderId)
			.orElseThrow(() -> new DoesNotExistException("Qr does no exist for order!"));
	}

}
