package br.com.fiap.techchallenge.payment.infra.gateway.database.entities;

import br.com.fiap.techchallenge.payment.domain.models.Payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

@DynamoDbBean
public class PaymentEntity {

	private UUID id;

	private BigDecimal amount;

	private boolean isPaid;

	private UUID externalPaymentId;

	private String qr;

	private UUID orderId;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	public PaymentEntity() {
	}

	public PaymentEntity(Payment payment) {
		this.id = payment.getId();
		this.amount = payment.getAmount();
		this.isPaid = payment.isPaid();
		this.externalPaymentId = payment.getExternalPaymentId();
		this.qr = payment.getQr();
		this.orderId = payment.getOrderId();
		this.createdAt = payment.getCreatedAt();
		this.updatedAt = payment.getUpdatedAt();
	}

	public PaymentEntity update(Payment payment) {
		this.id = payment.getId();
		this.amount = payment.getAmount();
		this.isPaid = payment.isPaid();
		this.externalPaymentId = payment.getExternalPaymentId();
		this.qr = payment.getQr();
		this.orderId = payment.getOrderId();
		this.createdAt = payment.getCreatedAt();
		this.updatedAt = LocalDateTime.now();
		return this;
	}

	public Payment toPayment() {
		return new Payment(id, amount, isPaid, externalPaymentId, qr, orderId, createdAt, updatedAt);
	}

	@DynamoDbPartitionKey
	public UUID getId() {
		return id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public boolean isPaid() {
		return isPaid;
	}

	public UUID getExternalPaymentId() {
		return externalPaymentId;
	}

	public String getQr() {
		return qr;
	}

	public UUID getOrderId() {
		return orderId;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

}
