package br.com.fiap.techchallenge.payment.application.usecase.payment.impl;

import br.com.fiap.techchallenge.payment.application.exceptions.DoesNotExistException;
import br.com.fiap.techchallenge.payment.application.gateway.client.PaymentClient;
import br.com.fiap.techchallenge.payment.application.persistence.PaymentPersistence;
import br.com.fiap.techchallenge.payment.application.usecase.payment.UpdatePaymentPaidUseCase;
import br.com.fiap.techchallenge.payment.domain.models.Payment;
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

	private final PaymentClient paymentClient;

	public UpdatePaymentPaidUseCaseImpl(PaymentPersistence persistence, PaymentClient paymentClient) {
		this.persistence = persistence;
		this.paymentClient = paymentClient;
	}

	@Override
	public void updateStatusByPaymentDataId(String dataId) {
		var paymentData = paymentClient.verifyPayment(dataId);
		var externalId = UUID.fromString(paymentData.getExternalReference());
		var paymentFound = persistence.findByExternalPaymentId(externalId)
			.orElseThrow(() -> new DoesNotExistException("Payment does no exist!"));

		if (paymentData.getStatus().equals("approved")) {
			paymentFound.setIsPaid(true);
			persistence.update(paymentFound);
		}

	}

	@Scheduled(fixedRate = 600000)
	@Transactional
	@Override
	public void updateOrderStatus() {
		LocalDateTime thirtyMinutesAgo = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"))
			.minusMinutes(2)
			.toLocalDateTime();
		List<Payment> paymentsFound = persistence.findByPaidIsFalseAndCreatedAtBefore(thirtyMinutesAgo);

		for (Payment payment : paymentsFound) {
			// todo: mandar uma mensagem para o ms de order informando a expiração do
			// pagamento
		}
	}

}
