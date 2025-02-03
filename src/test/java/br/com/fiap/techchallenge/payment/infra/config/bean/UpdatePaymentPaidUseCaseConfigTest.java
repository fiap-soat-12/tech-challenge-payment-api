package br.com.fiap.techchallenge.payment.infra.config.bean;

import br.com.fiap.techchallenge.payment.application.gateway.client.PaymentClient;
import br.com.fiap.techchallenge.payment.application.persistence.PaymentPersistence;
import br.com.fiap.techchallenge.payment.application.usecase.payment.impl.UpdatePaymentPaidUseCaseImpl;
import br.com.fiap.techchallenge.payment.infra.gateway.producer.OrderStatusUpdateProducer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class UpdatePaymentPaidUseCaseConfigTest {

	@Mock
	PaymentPersistence persistence;

	@Mock
	PaymentClient client;

	@Mock
	OrderStatusUpdateProducer producer;

	@InjectMocks
	private UpdatePaymentPaidUseCaseConfig updatePaymentPaidUseCaseConfig;

	@Test
	@DisplayName("Should Create a Singleton Instance Of UpdatePaymentPaidUseCaseImpl")
	void shouldCreateSingletonInstanceOfCreatePaymentUseCaseImpl() {
		var updatePaymentPaidUseCaseImpl = updatePaymentPaidUseCaseConfig.updatePaymentPaidUseCaseImpl(persistence,
				client, producer);

		assertNotNull(updatePaymentPaidUseCaseImpl);
		assertNotNull(persistence);
		assertNotNull(client);
		assertNotNull(producer);
		assertNotNull(updatePaymentPaidUseCaseConfig);
		assertInstanceOf(UpdatePaymentPaidUseCaseImpl.class, updatePaymentPaidUseCaseImpl);
	}

}
