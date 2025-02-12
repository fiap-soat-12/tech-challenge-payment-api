package br.com.fiap.techchallenge.payment.application.usecase.payment.impl;

import br.com.fiap.techchallenge.payment.application.exceptions.ApiClientRequestException;
import br.com.fiap.techchallenge.payment.application.gateway.client.PaymentClient;
import br.com.fiap.techchallenge.payment.application.persistence.PaymentPersistence;
import br.com.fiap.techchallenge.payment.application.usecase.payment.dto.PaymentCreateDTO;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

	private PaymentCreateDTO paymentCreateDTO;

	@BeforeEach
	void setUp() {
		this.buildArranges();
	}

	@Test
    @DisplayName("Should Create Payment")
    void shouldCreatePayment() {
        when(paymentClient.generateQrCode(any(PaymentClientDTO.class))).thenReturn(qrCode);
        when(persistence.create(any(Payment.class))).thenReturn(payment);

		assertDoesNotThrow(() -> createPaymentUseCase.create(paymentCreateDTO));

        verify(persistence).create(any(Payment.class));
    }

	@Test
	@DisplayName("Should Not Create Payment When Not Generate Qr Code")
	void shouldNotCreatePaymentWhenNotGenerateQrCode() {
		when(paymentClient.generateQrCode(any(PaymentClientDTO.class))).thenThrow(ApiClientRequestException.class);

		assertThrows(ApiClientRequestException.class,
				() -> createPaymentUseCase.create(paymentCreateDTO));

		verify(persistence, never()).create(any(Payment.class));
	}

	private void buildArranges() {
		qrCode = "QR Code";
		var totalAmount = new BigDecimal("100.00");
		var orderId = UUID.randomUUID();
		var externalPaymentId = UUID.randomUUID();

		payment = Payment.create(totalAmount, externalPaymentId, qrCode, orderId);
		paymentCreateDTO = new PaymentCreateDTO(orderId, totalAmount);
	}

}
