package br.com.fiap.techchallenge.payment.application.usecase.payment.impl;

import br.com.fiap.techchallenge.payment.application.exceptions.DoesNotExistException;
import br.com.fiap.techchallenge.payment.application.gateway.client.PaymentClient;
import br.com.fiap.techchallenge.payment.application.persistence.PaymentPersistence;
import br.com.fiap.techchallenge.payment.application.usecase.payment.UpdatePaymentPaidUseCase;
import br.com.fiap.techchallenge.payment.domain.models.Payment;
import br.com.fiap.techchallenge.payment.infra.gateway.producer.OrderStatusUpdateProducer;
import br.com.fiap.techchallenge.payment.infra.gateway.producer.dto.OrderStatusUpdateDTO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Transactional
public class UpdatePaymentPaidUseCaseImpl implements UpdatePaymentPaidUseCase {

	private final PaymentPersistence persistence;

	private final PaymentClient client;

	private final OrderStatusUpdateProducer producer;

	public UpdatePaymentPaidUseCaseImpl(PaymentPersistence persistence, PaymentClient client,
			OrderStatusUpdateProducer producer) {
		this.persistence = persistence;
		this.client = client;
		this.producer = producer;
	}

	@Override
	public void updatePaymentByDataId(String dataId) {
		var paymentData = client.verifyPayment(dataId);
		var externalId = UUID.fromString(paymentData.getExternalReference());
		var paymentFound = persistence.findByExternalPaymentId(externalId)
			.orElseThrow(() -> new DoesNotExistException("Payment does no exist!"));

		if (paymentData.getStatus().equals("approved")) {
			paymentFound.setIsPaid(true);
			persistence.update(paymentFound);
			producer.sendMessage(new OrderStatusUpdateDTO(paymentFound.getOrderId(), paymentFound.isPaid()));
		}

	}

	@Override
	@Transactional
	@Scheduled(fixedRate = 600000)
	public void updateOrderStatus() {
		LocalDateTime thirtyMinutesAgo = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"))
			.minusMinutes(30)
			.toLocalDateTime();
		List<Payment> paymentsFound = persistence.findByPaidIsFalseAndCreatedAtBefore(thirtyMinutesAgo);
		paymentsFound
			.forEach(payment -> producer.sendMessage(new OrderStatusUpdateDTO(payment.getOrderId(), payment.isPaid())));

	}

}
