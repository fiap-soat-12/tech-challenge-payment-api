package br.com.fiap.techchallenge.payment.infra.gateway.database.repository.impl;

import br.com.fiap.techchallenge.payment.domain.models.Payment;
import br.com.fiap.techchallenge.payment.infra.gateway.database.entities.PaymentEntity;
import br.com.fiap.techchallenge.payment.infra.gateway.database.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static br.com.fiap.techchallenge.payment.domain.models.enums.PaymentStatusEnum.FINISHED;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentPersistenceImplTest {

	@Mock
	private PaymentRepository repository;

	@InjectMocks
	private PaymentPersistenceImpl paymentPersistence;

	private Payment payment;

	private PaymentEntity paymentEntity;

	@BeforeEach
	void setUp() {
		this.buildArranges();
	}

	@Test
    @DisplayName("Should create Payment Entity")
    void shouldCreatePaymentEntity() {
		when(repository.save(any(PaymentEntity.class))).thenReturn(paymentEntity);

		var result = paymentPersistence.create(payment);

		assertNotNull(result);
		assertEquals(payment.getOrderId(), result.getOrderId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getAmount(), result.getAmount());
        assertTrue(result.isPaid());
        assertEquals(payment.getExternalPaymentId(), result.getExternalPaymentId());
        assertEquals(payment.getQr(), result.getQr());
        assertEquals(payment.getOrderId(), result.getOrderId());
        assertEquals(payment.getCreatedAt(), result.getCreatedAt());
        assertEquals(payment.getUpdatedAt(), result.getUpdatedAt());
		verify(repository).save(any(PaymentEntity.class));
	}

	@Test
    @DisplayName("Should update Payment Entity")
    void shouldUpdatePaymentEntity() {
        when(repository.save(any(PaymentEntity.class))).thenReturn(paymentEntity);

		var result = paymentPersistence.update(payment);

		assertNotNull(result);
        assertEquals(payment.getOrderId(), result.getOrderId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getAmount(), result.getAmount());
        assertTrue(result.isPaid());
        assertEquals(payment.getExternalPaymentId(), result.getExternalPaymentId());
        assertEquals(payment.getQr(), result.getQr());
        assertEquals(payment.getOrderId(), result.getOrderId());
        assertEquals(payment.getCreatedAt(), result.getCreatedAt());
        assertEquals(payment.getUpdatedAt(), result.getUpdatedAt());
        verify(repository).save(any(PaymentEntity.class));
	}

	@Test
    @DisplayName("Should return payment if exists when search for order id")
    void shouldReturnPaymentIfExistsWhenSearchForOrderId() {
		when(repository.findByOrderId(payment.getOrderId())).thenReturn(Optional.of(paymentEntity));

		var result = paymentPersistence.findByOrderId(payment.getOrderId());

		assertTrue(result.isPresent());
		assertEquals(payment.getOrderId(), result.get().getOrderId());
		verify(repository).findByOrderId(payment.getOrderId());
	}

	@Test
	@DisplayName("Should return payment empty if not exists when search for order id")
    void shouldReturnPaymentEmptyIfNotExistsWhenSearchForOrderId() {
        when(repository.findByOrderId(payment.getOrderId())).thenReturn(Optional.empty());

        var result = paymentPersistence.findByOrderId(payment.getOrderId());

        assertFalse(result.isPresent());
        verify(repository).findByOrderId(payment.getOrderId());
    }

	@Test
    @DisplayName("Should return payment if exists when search for external payment id")
    void shouldReturnPaymentIfExistsWhenSearchForExternalPaymentId() {
		when(repository.findByExternalPaymentId(payment.getExternalPaymentId())).thenReturn(Optional.of(paymentEntity));

		var result = paymentPersistence.findByExternalPaymentId(payment.getExternalPaymentId());

		assertTrue(result.isPresent());
		assertEquals(payment.getExternalPaymentId(), result.get().getExternalPaymentId());
		verify(repository).findByExternalPaymentId(payment.getExternalPaymentId());
	}

	@Test
    @DisplayName("Should return payment empty if exists when search for external payment id")
    void shouldReturnPaymentEmptyIfExistsWhenSearchForExternalPaymentId() {
        when(repository.findByExternalPaymentId(payment.getExternalPaymentId())).thenReturn(Optional.empty());

        var result = paymentPersistence.findByExternalPaymentId(payment.getExternalPaymentId());

        assertFalse(result.isPresent());
        verify(repository).findByExternalPaymentId(payment.getExternalPaymentId());
    }

	@Test
	@DisplayName("Should return list of payments when the pain is false and createdAt is Less than now")
	void shouldReturnListOfPaymentsWhenThePainIsFalseAndCreatedAtIsLessThanNow() {
		var paymentEntities = List.of(paymentEntity, paymentEntity);

		when(repository.findByStatusIsPendingAndCreatedAtBefore(payment.getCreatedAt())).thenReturn(paymentEntities);

		var result = paymentPersistence.findByStatusIsPendingAndCreatedAtBefore(payment.getCreatedAt());

		assertNotNull(result);
		assertEquals(2, result.size());
		verify(repository).findByStatusIsPendingAndCreatedAtBefore(payment.getCreatedAt());
	}

	@Test
    @DisplayName("Should return list of payments empty when the pain is false and createdAt is Less than now")
    void shouldReturnListOfPaymentsEmptyWhenThePainIsFalseAndCreatedAtIsLessThanNow() {
        when(repository.findByStatusIsPendingAndCreatedAtBefore(payment.getCreatedAt())).thenReturn(List.of());

        var result = paymentPersistence.findByStatusIsPendingAndCreatedAtBefore(payment.getCreatedAt());

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repository).findByStatusIsPendingAndCreatedAtBefore(payment.getCreatedAt());
    }

	private void buildArranges() {
		payment = new Payment(UUID.randomUUID(), new BigDecimal("100.00"), true, FINISHED, UUID.randomUUID(), "QR Code",
				UUID.randomUUID(), LocalDateTime.now(), LocalDateTime.now());
		paymentEntity = new PaymentEntity(payment);
	}

}
