package br.com.fiap.techchallenge.payment.infra.config.bean;

import br.com.fiap.techchallenge.payment.application.persistence.PaymentPersistence;
import br.com.fiap.techchallenge.payment.application.usecase.payment.impl.FindPaymentQrByOrderIdUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FindPaymentQrByOrderIdUseCaseConfig {

	@Bean
	public FindPaymentQrByOrderIdUseCaseImpl findPaymentQrByOrderIdUseCase(PaymentPersistence paymentPersistence) {
		return new FindPaymentQrByOrderIdUseCaseImpl(paymentPersistence);
	}

}
