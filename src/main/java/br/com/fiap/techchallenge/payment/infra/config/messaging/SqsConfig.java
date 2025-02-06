package br.com.fiap.techchallenge.payment.infra.config.messaging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import java.net.URI;

@Configuration
public class SqsConfig {

	@Bean
	public SqsAsyncClient sqsAsyncClient() {
		return SqsAsyncClient.builder()
			.endpointOverride(URI.create("https://localhost.localstack.cloud:4566"))
			.region(Region.US_EAST_1)
			.credentialsProvider(DefaultCredentialsProvider.create())
			.build();
	}

}