package br.com.fiap.techchallenge.payment.application.usecase.payment.impl;

import br.com.fiap.techchallenge.payment.application.exceptions.DoesNotExistException;
import br.com.fiap.techchallenge.payment.application.persistence.PaymentPersistence;
import br.com.fiap.techchallenge.payment.domain.models.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindPaymentQrByOrderIdUseCaseImplTest {

	@Mock
	private PaymentPersistence persistence;

	@InjectMocks
	private FindPaymentQrByOrderIdUseCaseImpl findPaymentQrByOrderIdUseCase;

	private UUID orderId;

	private Payment payment;

	@BeforeEach
	void setUp() {
		this.buildArranges();
	}

	@Test
    @DisplayName("Should update payment to true when paid")
    void shouldUpdatePaymentToTrueWhenPaid() {
        when(persistence.findByOrderId(any(UUID.class))).thenReturn(Optional.of(payment));

        var result = findPaymentQrByOrderIdUseCase.findQrByOrderId(orderId);

        assertNotNull(result);
        assertEquals(payment.getOrderId(), result.getOrderId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getAmount(), result.getAmount());
        assertFalse(result.isPaid());
        assertEquals(payment.getExternalPaymentId(), result.getExternalPaymentId());
        assertEquals(payment.getQr(), result.getQr());
        assertEquals(payment.getOrderId(), result.getOrderId());
        assertEquals(payment.getCreatedAt(), result.getCreatedAt());
        assertEquals(payment.getUpdatedAt(), result.getUpdatedAt());
        verify(persistence).findByOrderId(any(UUID.class));
    }

	@Test
    @DisplayName("Should not update payment when not paid")
    void shouldNotUpdatePaymentWhenNotPaid() {
        when(persistence.findByOrderId(any(UUID.class))).thenReturn(Optional.empty());

        DoesNotExistException exception = assertThrows(DoesNotExistException.class,
                () -> findPaymentQrByOrderIdUseCase.findQrByOrderId(orderId));

        assertEquals("Qr does no exist for order!", exception.getMessage());
        verify(persistence).findByOrderId(any(UUID.class));
    }

	private void buildArranges() {
		orderId = UUID.randomUUID();

		payment = new Payment(UUID.randomUUID(), new BigDecimal("100.00"), false, UUID.randomUUID(), "QR Code", orderId,
				LocalDateTime.now(), LocalDateTime.now());
	}

}
