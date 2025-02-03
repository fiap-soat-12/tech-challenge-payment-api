package br.com.fiap.techchallenge.payment.infra.config.bean;

import br.com.fiap.techchallenge.payment.application.gateway.client.PaymentClient;
import br.com.fiap.techchallenge.payment.application.persistence.PaymentPersistence;
import br.com.fiap.techchallenge.payment.application.usecase.payment.impl.UpdatePaymentPaidUseCaseImpl;
import br.com.fiap.techchallenge.payment.infra.gateway.producer.OrderStatusUpdateProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpdatePaymentPaidUseCaseConfig {

	@Bean
	public UpdatePaymentPaidUseCaseImpl updatePaymentPaidUseCaseImpl(PaymentPersistence persistence,
			PaymentClient client, OrderStatusUpdateProducer producer) {
		return new UpdatePaymentPaidUseCaseImpl(persistence, client, producer);
	}

}
