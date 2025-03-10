package br.com.fiap.techchallenge.payment.infra.gateway.database.entities;

import br.com.fiap.techchallenge.payment.domain.models.Payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import br.com.fiap.techchallenge.payment.domain.models.enums.PaymentStatusEnum;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

@DynamoDbBean
public class PaymentEntity {

	private UUID id;

	private BigDecimal amount;

	private boolean isPaid;

	private PaymentStatusEnum paymentStatus;

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
		this.paymentStatus = payment.getPaymentStatus();
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
		this.paymentStatus = payment.getPaymentStatus();
		this.externalPaymentId = payment.getExternalPaymentId();
		this.qr = payment.getQr();
		this.orderId = payment.getOrderId();
		this.createdAt = payment.getCreatedAt();
		this.updatedAt = LocalDateTime.now();
		return this;
	}

	public Payment toPayment() {
		return new Payment(id, amount, isPaid, paymentStatus, externalPaymentId, qr, orderId, createdAt, updatedAt);
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

	public PaymentStatusEnum getPaymentStatus() {
		return paymentStatus;
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

	public void setId(UUID id) {
		this.id = id;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setPaid(boolean paid) {
		isPaid = paid;
	}

	public void setPaymentStatus(PaymentStatusEnum paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public void setExternalPaymentId(UUID externalPaymentId) {
		this.externalPaymentId = externalPaymentId;
	}

	public void setQr(String qr) {
		this.qr = qr;
	}

	public void setOrderId(UUID orderId) {
		this.orderId = orderId;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

}
