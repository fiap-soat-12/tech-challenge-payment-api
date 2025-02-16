package br.com.fiap.techchallenge.payment.application.persistence;

import br.com.fiap.techchallenge.payment.domain.models.Payment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentPersistence {

	Payment create(Payment payment);

	Payment update(Payment payment);

	Optional<Payment> findByOrderId(UUID orderId);

	Optional<Payment> findByExternalPaymentId(UUID externalPaymentId);

	List<Payment> findByStatusIsPendingAndCreatedAtBefore(LocalDateTime createdAt);

}
