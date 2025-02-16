package br.com.fiap.techchallenge.payment.infra.gateway.database.repository;

import br.com.fiap.techchallenge.payment.infra.gateway.database.entities.PaymentEntity;
import java.util.*;
import java.time.LocalDateTime;

public interface PaymentRepository {

	PaymentEntity save(PaymentEntity paymentEntity);

	Optional<PaymentEntity> findByOrderId(UUID orderId);

	Optional<PaymentEntity> findByExternalPaymentId(UUID externalPaymentId);

	List<PaymentEntity> findByStatusIsPendingAndCreatedAtBefore(LocalDateTime createdAt);

}