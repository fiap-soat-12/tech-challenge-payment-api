package br.com.fiap.techchallenge.payment.domain.models;

import br.com.fiap.techchallenge.payment.domain.models.enums.PaymentStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static br.com.fiap.techchallenge.payment.domain.models.enums.PaymentStatusEnum.PENDING;

public class Payment {

	private final UUID id;

	private final BigDecimal amount;

	private boolean isPaid;

	private PaymentStatusEnum status;

	private final UUID externalPaymentId;

	private final String qr;

	private final UUID orderId;

	private final LocalDateTime createdAt;

	private final LocalDateTime updatedAt;

	public Payment(UUID id, BigDecimal amount, Boolean isPaid, PaymentStatusEnum status, UUID externalPaymentId,
			String qr, UUID orderId, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.amount = amount;
		this.isPaid = isPaid;
		this.status = status;
		this.externalPaymentId = externalPaymentId;
		this.qr = qr;
		this.orderId = orderId;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public static Payment create(BigDecimal amount, UUID externalPaymentId, String qr, UUID orderId) {
		return new Payment(UUID.randomUUID(), amount, false, PENDING, externalPaymentId, qr, orderId,
				LocalDateTime.now(), LocalDateTime.now());
	}

	public Payment setPaid(Boolean isPaid, PaymentStatusEnum status) {
		this.isPaid = isPaid;
		this.status = status;
		return this;
	}

	public UUID getId() {
		return id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public boolean isPaid() {
		return isPaid;
	}

	public PaymentStatusEnum getStatus() {
		return status;
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

	public void setStatus(PaymentStatusEnum status) {
		this.status = status;
	}

}
