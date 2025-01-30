package br.com.fiap.techchallenge.payment.infra.gateway.database.entities;

import br.com.fiap.techchallenge.payment.domain.models.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentEntityTest {

	private PaymentEntity paymentEntity;

	private Payment payment;

	@BeforeEach
	void setUp() {
		this.buildArranges();
	}

	@Test
	@DisplayName("Should return Payment Entity attributes as the object was created by the constructor")
	void shouldReturnPaymentEntityAttributesAsTheObjectWasCreatedByTheConstructor() {
		paymentEntity = new PaymentEntity(payment);

		assertNotNull(paymentEntity);
		assertEquals(payment.getId(), paymentEntity.getId());
		assertEquals(payment.getAmount(), paymentEntity.getAmount());
		assertTrue(paymentEntity.isPaid());
		assertEquals(payment.getExternalPaymentId(), paymentEntity.getExternalPaymentId());
		assertEquals(payment.getQr(), paymentEntity.getQr());
		assertEquals(payment.getOrderId(), paymentEntity.getOrderId());
		assertEquals(payment.getCreatedAt(), paymentEntity.getCreatedAt());
		assertEquals(payment.getUpdatedAt(), paymentEntity.getUpdatedAt());
	}

	@Test
	@DisplayName("Should return Payment Entity attributes as the object was created by the update method")
	void shouldReturnPaymentEntityAttributesAsTheObjectWasCreatedByTheUpdateMethod() {
		paymentEntity = new PaymentEntity().update(payment);

		assertNotNull(paymentEntity);
		assertEquals(payment.getId(), paymentEntity.getId());
		assertEquals(payment.getAmount(), paymentEntity.getAmount());
		assertTrue(paymentEntity.isPaid());
		assertEquals(payment.getExternalPaymentId(), paymentEntity.getExternalPaymentId());
		assertEquals(payment.getQr(), paymentEntity.getQr());
		assertEquals(payment.getOrderId(), paymentEntity.getOrderId());
		assertEquals(payment.getCreatedAt(), paymentEntity.getCreatedAt());
		assertNotNull(paymentEntity.getUpdatedAt());
	}

	@Test
	@DisplayName("Should return Payment attributes as the object was created by the Payment Entity")
	void shouldReturnPaymentAttributesAsTheObjectWasCreatedByThePaymentEntity() {
		paymentEntity = new PaymentEntity(payment);
		var newPayment = paymentEntity.toPayment();

		assertNotNull(newPayment);
		assertEquals(paymentEntity.getId(), newPayment.getId());
		assertEquals(paymentEntity.getAmount(), newPayment.getAmount());
		assertTrue(newPayment.isPaid());
		assertEquals(paymentEntity.getExternalPaymentId(), newPayment.getExternalPaymentId());
		assertEquals(paymentEntity.getQr(), newPayment.getQr());
		assertEquals(paymentEntity.getOrderId(), newPayment.getOrderId());
		assertEquals(paymentEntity.getCreatedAt(), newPayment.getCreatedAt());
		assertEquals(paymentEntity.getUpdatedAt(), newPayment.getUpdatedAt());
	}

	private void buildArranges() {
		payment = new Payment(UUID.randomUUID(), new BigDecimal("100.00"), true, UUID.randomUUID(), "QR Code",
				UUID.randomUUID(), LocalDateTime.now(), LocalDateTime.now());
	}

}
