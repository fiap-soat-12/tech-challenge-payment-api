package br.com.fiap.techchallenge.payment.application.usecase.payment.impl;

import br.com.fiap.techchallenge.payment.application.gateway.client.PaymentClient;
import br.com.fiap.techchallenge.payment.application.persistence.PaymentPersistence;
import br.com.fiap.techchallenge.payment.application.usecase.payment.dto.PaymentOrderCreateDTO;
import br.com.fiap.techchallenge.payment.domain.models.Payment;
import br.com.fiap.techchallenge.payment.infra.gateway.client.cotroller.dto.PaymentClientDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreatePaymentUseCaseImplTest {

	@Mock
	private PaymentPersistence persistence;

	@Mock
	private PaymentClient paymentClient;

	@InjectMocks
	private CreatePaymentUseCaseImpl createPaymentUseCase;

	private String qrCode;

	private Payment payment;

	private PaymentOrderCreateDTO paymentOrderCreateDTO;

	@BeforeEach
	void setUp() {
		this.buildArranges();
	}

	@Test
    @DisplayName("Should create Payment")
    void shouldCreatePayment() {
        when(paymentClient.generateQrCode(any(PaymentClientDTO.class))).thenReturn(qrCode);
        when(persistence.create(any(Payment.class))).thenReturn(payment);

        var result = createPaymentUseCase.create(paymentOrderCreateDTO);

        assertNotNull(result);
        assertEquals(payment.getOrderId(), result.getOrderId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getAmount(), result.getAmount());
        assertEquals(payment.getExternalPaymentId(), result.getExternalPaymentId());
        assertEquals(payment.getQr(), result.getQr());
        assertEquals(payment.getOrderId(), result.getOrderId());
        assertEquals(payment.getCreatedAt(), result.getCreatedAt());
        assertEquals(payment.getUpdatedAt(), result.getUpdatedAt());
        verify(paymentClient).generateQrCode(any(PaymentClientDTO.class));
        verify(persistence).create(any(Payment.class));
    }

	private void buildArranges() {
		qrCode = "QR Code";
		var totalAmount = new BigDecimal("100.00");
		var orderId = UUID.randomUUID();
		var externalPaymentId = UUID.randomUUID();

		payment = Payment.create(totalAmount, externalPaymentId, qrCode, orderId);
		paymentOrderCreateDTO = new PaymentOrderCreateDTO(orderId, totalAmount);
	}

}
