package br.com.fiap.techchallenge.payment.infra.config.bean;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class DynamoDbConfigTest {

	@InjectMocks
	private DynamoDbConfig dynamoDbConfig;

	@Test
	@DisplayName("Should Create a Singleton Instance Of DynamoDbClient")
	void shouldCreateSingletonInstanceOfCreatePaymentUseCaseImpl() {
		var dynamoDbClient = dynamoDbConfig.dynamoDbClient();

		assertNotNull(dynamoDbClient);
		assertInstanceOf(DynamoDbClient.class, dynamoDbClient);
	}

}
