package br.com.fiap.techchallenge.payment.infra.config.bean;

import br.com.fiap.techchallenge.payment.application.gateway.client.PaymentClient;
import br.com.fiap.techchallenge.payment.application.persistence.PaymentPersistence;
import br.com.fiap.techchallenge.payment.application.usecase.payment.impl.CreatePaymentUseCaseImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class CreatePaymentUseCaseConfigTest {

	@Mock
	PaymentPersistence persistence;

	@Mock
	PaymentClient paymentClient;

	@InjectMocks
	private CreatePaymentUseCaseConfig createPaymentUseCaseConfig;

	@Test
	@DisplayName("Should Create a Singleton Instance Of CreatePaymentUseCaseImpl")
	void shouldCreateSingletonInstanceOfCreatePaymentUseCaseImpl() {
		var createPaymentUseCaseImpl = createPaymentUseCaseConfig.createPaymentUseCaseImpl(persistence, paymentClient);

		assertNotNull(createPaymentUseCaseImpl);
		assertNotNull(persistence);
		assertNotNull(paymentClient);
		assertInstanceOf(CreatePaymentUseCaseImpl.class, createPaymentUseCaseImpl);
	}

}
