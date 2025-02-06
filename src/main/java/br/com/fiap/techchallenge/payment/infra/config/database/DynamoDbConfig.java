package br.com.fiap.techchallenge.payment.infra.config.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import java.net.URI;

@Configuration
public class DynamoDbConfig {

	@Bean
	public DynamoDbClient dynamoDbClient() {
		return DynamoDbClient.builder()
			.endpointOverride(URI.create("https://localhost.localstack.cloud:4566"))
			.region(Region.US_EAST_1)
			.build();
	}

}
