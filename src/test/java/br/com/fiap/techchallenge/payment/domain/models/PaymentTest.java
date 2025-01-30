package br.com.fiap.techchallenge.payment.domain.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentTest {

	private Payment payment;

	private UUID id;

	private BigDecimal amount;

	private boolean isPaid;

	private UUID externalPaymentId;

	private String qr;

	private UUID orderId;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	@BeforeEach
	void setUp() {
		this.buildArranges();
	}

	@Test
	@DisplayName("Should return Payment attributes as the object was created by the constructor")
	void shouldReturnPaymentAttributesAsTheObjectWasCreatedByTheConstructor() {
		payment = new Payment(id, amount, isPaid, externalPaymentId, qr, orderId, createdAt, updatedAt);

		assertNotNull(payment);
		assertEquals(id, payment.getId());
		assertEquals(amount, payment.getAmount());
		assertTrue(payment.isPaid());
		assertEquals(externalPaymentId, payment.getExternalPaymentId());
		assertEquals(qr, payment.getQr());
		assertEquals(orderId, payment.getOrderId());
		assertEquals(createdAt, payment.getCreatedAt());
		assertEquals(updatedAt, payment.getUpdatedAt());
	}

	@Test
	@DisplayName("Should return Payment attributes as the object was created by the create method")
	void shouldReturnPaymentAttributesAsTheObjectWasCreatedByTheCreateMethod() {
		payment = Payment.create(amount, externalPaymentId, qr, orderId);

		assertNotNull(payment);
		assertEquals(amount, payment.getAmount());
		assertFalse(payment.isPaid());
		assertEquals(externalPaymentId, payment.getExternalPaymentId());
		assertEquals(qr, payment.getQr());
		assertEquals(orderId, payment.getOrderId());
	}

	private void buildArranges() {
		id = UUID.randomUUID();
		amount = new BigDecimal("100.00");
		isPaid = true;
		externalPaymentId = UUID.randomUUID();
		qr = "QR Code";
		orderId = UUID.randomUUID();
		createdAt = LocalDateTime.now();
		updatedAt = LocalDateTime.now();
	}

}
