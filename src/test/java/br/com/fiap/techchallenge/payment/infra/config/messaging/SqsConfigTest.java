package br.com.fiap.techchallenge.payment.infra.config.messaging;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class SqsConfigTest {

	@InjectMocks
	private SqsConfig sqsConfig;

	@Test
	@DisplayName("Should Create a Singleton Instance Of SqsAsyncClient")
	void shouldCreateSingletonInstanceOfCreatePaymentUseCaseImpl() {
		var sqsAsyncClient = sqsConfig.sqsAsyncClient();

		assertNotNull(sqsAsyncClient);
		assertInstanceOf(SqsAsyncClient.class, sqsAsyncClient);
	}

}
