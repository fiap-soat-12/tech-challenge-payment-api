package br.com.fiap.techchallenge.payment.infra.gateway.database.repository.impl;

import br.com.fiap.techchallenge.payment.infra.gateway.database.entities.PaymentEntity;
import br.com.fiap.techchallenge.payment.infra.gateway.database.repository.PaymentRepository;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.mercadopago.resources.payment.PaymentStatus.PENDING;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

	private final DynamoDbTemplate dynamoDbTemplate;

	public PaymentRepositoryImpl(DynamoDbTemplate dynamoDbTemplate) {
		this.dynamoDbTemplate = dynamoDbTemplate;
	}

	@Override
	public PaymentEntity save(PaymentEntity paymentEntity) {
		dynamoDbTemplate.save(paymentEntity);
		return paymentEntity;
	}

	@Override
	public Optional<PaymentEntity> findByOrderId(UUID orderId) {
		ScanEnhancedRequest scanEnhancedRequest = ScanEnhancedRequest.builder()
			.filterExpression(Expression.builder()
				.expression("orderId = :orderIdVal")
				.expressionValues(Map.of(":orderIdVal", AttributeValue.builder().s(orderId.toString()).build()))
				.build())
			.build();

		var history = dynamoDbTemplate.scan(scanEnhancedRequest, PaymentEntity.class);
		return history.items().stream().findFirst();
	}

	@Override
	public Optional<PaymentEntity> findByExternalPaymentId(UUID externalPaymentId) {
		ScanEnhancedRequest scanEnhancedRequest = ScanEnhancedRequest.builder()
			.filterExpression(Expression.builder()
				.expression("externalPaymentId = :externalPaymentIdVal")
				.expressionValues(Map.of(":externalPaymentIdVal",
						AttributeValue.builder().s(externalPaymentId.toString()).build()))
				.build())
			.build();

		var history = dynamoDbTemplate.scan(scanEnhancedRequest, PaymentEntity.class);
		return history.items().stream().findFirst();
	}

	@Override
	public List<PaymentEntity> findByStatusIsPendingAndCreatedAtBefore(LocalDateTime createdAt) {
		ScanEnhancedRequest scanEnhancedRequest = ScanEnhancedRequest.builder()
			.filterExpression(Expression.builder()
				.expression("status = :statusVal AND createdAt < :createdAtVal")
				.expressionValues(Map.of(":statusVal", AttributeValue.builder().s(PENDING).build(), ":createdAtVal",
						AttributeValue.builder().s(createdAt.toString()).build()))
				.build())
			.build();

		var history = dynamoDbTemplate.scan(scanEnhancedRequest, PaymentEntity.class);
		return history.items().stream().toList();
	}

}