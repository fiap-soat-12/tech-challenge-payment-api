package br.com.fiap.techchallenge.payment.infra.config.bean;

import br.com.fiap.techchallenge.payment.application.gateway.client.PaymentClient;
import br.com.fiap.techchallenge.payment.application.persistence.PaymentPersistence;
import br.com.fiap.techchallenge.payment.application.usecase.payment.impl.UpdatePaymentPaidUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpdatePaymentPaidUseCaseConfig {

	@Bean
	public UpdatePaymentPaidUseCaseImpl updatePaymentPaidUseCaseImpl(PaymentPersistence paymentPersistence,
			PaymentClient paymentClient) {
		return new UpdatePaymentPaidUseCaseImpl(paymentPersistence, paymentClient);
	}

}
