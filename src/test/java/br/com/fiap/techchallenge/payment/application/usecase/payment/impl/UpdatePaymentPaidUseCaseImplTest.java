package br.com.fiap.techchallenge.payment.application.usecase.payment.impl;

import br.com.fiap.techchallenge.payment.application.exceptions.DoesNotExistException;
import br.com.fiap.techchallenge.payment.application.gateway.client.PaymentClient;
import br.com.fiap.techchallenge.payment.application.persistence.PaymentPersistence;
import br.com.fiap.techchallenge.payment.domain.models.Payment;
import br.com.fiap.techchallenge.payment.infra.gateway.client.cotroller.dto.PaymentStatusClientDTO;
import br.com.fiap.techchallenge.payment.infra.gateway.producer.OrderStatusUpdateProducer;
import br.com.fiap.techchallenge.payment.infra.gateway.producer.dto.OrderStatusUpdateDTO;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdatePaymentPaidUseCaseImplTest {

	@Mock
	private PaymentPersistence persistence;

	@Mock
	private PaymentClient client;

	@Mock
	private OrderStatusUpdateProducer producer;

	@InjectMocks
	private UpdatePaymentPaidUseCaseImpl updatePaymentPaidUseCase;

	private String dataId;

	private Long paymentClientId;

	private String paymentClientStatus;

	private UUID externalPaymentId;

	private Payment payment;

	private PaymentStatusClientDTO paymentStatusClientDTO;

	@BeforeEach
	void setUp() {
		this.buildArranges();
	}

	@Test
	@DisplayName("Should update payment to true when paid")
	void shouldUpdatePaymentToTrueWhenPaid() {
		paymentClientStatus = "approved";
		paymentStatusClientDTO = new PaymentStatusClientDTO(externalPaymentId.toString(), paymentClientId,
				paymentClientStatus);

		when(client.verifyPayment(anyString())).thenReturn(paymentStatusClientDTO);
		when(persistence.findByExternalPaymentId(any(UUID.class))).thenReturn(Optional.of(payment));

		payment.setIsPaid(true);

		when(persistence.update(any(Payment.class))).thenReturn(payment);

		assertDoesNotThrow(
				() -> producer.sendMessage(new OrderStatusUpdateDTO(payment.getOrderId(), payment.isPaid())));

		updatePaymentPaidUseCase.updatePaymentByDataId(dataId);

		assertTrue(payment.isPaid());
		verify(client).verifyPayment(anyString());
		verify(persistence).findByExternalPaymentId(any(UUID.class));
		verify(persistence).update(any(Payment.class));
		verify(producer, times(2)).sendMessage(any(OrderStatusUpdateDTO.class));
	}

	@Test
	@DisplayName("Should not update payment when not found")
	void shouldNotUpdatePaymentWhenNotFound() {
		paymentStatusClientDTO = new PaymentStatusClientDTO(externalPaymentId.toString(), paymentClientId,
				paymentClientStatus);

		when(client.verifyPayment(anyString())).thenReturn(paymentStatusClientDTO);
		when(persistence.findByExternalPaymentId(any(UUID.class))).thenReturn(Optional.empty());

		DoesNotExistException exception = assertThrows(DoesNotExistException.class,
				() -> updatePaymentPaidUseCase.updatePaymentByDataId(dataId));

		assertEquals("Payment does no exist!", exception.getMessage());

		verify(client).verifyPayment(anyString());
		verify(persistence).findByExternalPaymentId(any(UUID.class));
	}

	@Test
	@DisplayName("Should not update payment when not paid")
	void shouldNotUpdatePaymentWhenNotPaid() {
		paymentClientStatus = "unapproved";
		paymentStatusClientDTO = new PaymentStatusClientDTO(externalPaymentId.toString(), paymentClientId,
				paymentClientStatus);

		when(client.verifyPayment(anyString())).thenReturn(paymentStatusClientDTO);
		when(persistence.findByExternalPaymentId(any(UUID.class))).thenReturn(Optional.of(payment));

		updatePaymentPaidUseCase.updatePaymentByDataId(dataId);

		assertFalse(payment.isPaid());
		verify(client).verifyPayment(anyString());
		verify(persistence).findByExternalPaymentId(any(UUID.class));
	}

	@Test
	@DisplayName("Should Update Order Status")
	void shouldUpdateOrderStatus() {
		when(persistence.findByPaidIsFalseAndCreatedAtBefore(any(LocalDateTime.class))).thenReturn(List.of(payment));

		assertDoesNotThrow(
				() -> producer.sendMessage(new OrderStatusUpdateDTO(payment.getOrderId(), payment.isPaid())));

		updatePaymentPaidUseCase.updateOrderStatus();

		assertFalse(payment.isPaid());
		verify(persistence).findByPaidIsFalseAndCreatedAtBefore(any(LocalDateTime.class));
		verify(producer, times(2)).sendMessage(any(OrderStatusUpdateDTO.class));
	}

	private void buildArranges() {
		dataId = "100116848319";
		paymentClientId = Long.valueOf(dataId);
		externalPaymentId = UUID.randomUUID();
		var totalAmount = new BigDecimal("100.00");
		var orderId = UUID.randomUUID();

		payment = new Payment(UUID.randomUUID(), totalAmount, false, externalPaymentId, "QR Code", orderId,
				LocalDateTime.now(), LocalDateTime.now());
	}

}
