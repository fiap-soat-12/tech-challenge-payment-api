package br.com.fiap.techchallenge.payment.domain.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Payment {

	private final UUID id;

	private final BigDecimal amount;

	private Boolean isPaid;

	private final UUID externalPaymentId;

	private final String qr;

	private final UUID orderId;

	private final LocalDateTime createdAt;

	private final LocalDateTime updatedAt;

	public Payment(UUID id, BigDecimal amount, Boolean isPaid, UUID externalPaymentId, String qr, UUID orderId,
			LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.amount = amount;
		this.isPaid = isPaid;
		this.externalPaymentId = externalPaymentId;
		this.qr = qr;
		this.orderId = orderId;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public static Payment create(BigDecimal amount, UUID externalPaymentId, String qr, UUID orderId) {
		return new Payment(UUID.randomUUID(), amount, null, externalPaymentId, qr, orderId, LocalDateTime.now(),
				LocalDateTime.now());
	}

	public UUID getId() {
		return id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public Boolean isPaid() {
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

	public void setIsPaid(Boolean isPaid) {
		this.isPaid = isPaid;
	}

}
