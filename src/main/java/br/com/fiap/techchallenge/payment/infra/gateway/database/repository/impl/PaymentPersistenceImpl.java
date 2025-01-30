package br.com.fiap.techchallenge.payment.infra.gateway.database.repository.impl;

import br.com.fiap.techchallenge.payment.application.persistence.PaymentPersistence;
import br.com.fiap.techchallenge.payment.domain.models.Payment;
import br.com.fiap.techchallenge.payment.infra.gateway.database.entities.PaymentEntity;
import br.com.fiap.techchallenge.payment.infra.gateway.database.repository.PaymentRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class PaymentPersistenceImpl implements PaymentPersistence {

	private final PaymentRepository repository;

	public PaymentPersistenceImpl(PaymentRepository repository) {
		this.repository = repository;
	}

	@Override
	public Payment create(Payment payment) {
		var productEntity = new PaymentEntity(payment);
		var productSaved = repository.save(productEntity);
		return productSaved.toPayment();
	}

	@Override
	public Payment update(Payment payment) {
		var paymentEntity = new PaymentEntity().update(payment);
		var paymentUpdated = repository.save(paymentEntity);
		return paymentUpdated.toPayment();
	}

	@Override
	public Optional<Payment> findByOrderId(UUID orderId) {
		var orderFound = repository.findByOrderId(orderId);
		return orderFound.map(PaymentEntity::toPayment);
	}

	@Override
	public Optional<Payment> findByExternalPaymentId(UUID externalPaymentId) {
		var orderFound = repository.findByExternalPaymentId(externalPaymentId);
		return orderFound.map(PaymentEntity::toPayment);
	}

	@Override
	public List<Payment> findByPaidIsFalseAndCreatedAtBefore(LocalDateTime createdAt) {
		var orderFound = repository.findByPaidIsFalseAndCreatedAtBefore(createdAt);
		return orderFound.stream().map(PaymentEntity::toPayment).toList();
	}

}
