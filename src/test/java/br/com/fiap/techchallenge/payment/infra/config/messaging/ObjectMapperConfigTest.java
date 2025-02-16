package br.com.fiap.techchallenge.payment.infra.config.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ObjectMapperConfigTest {

	@InjectMocks
	private ObjectMapperConfig objectMapperConfig;

	@Test
	@DisplayName("Should Create a Singleton Instance Of ObjectMapper")
	void shouldCreateSingletonInstanceOfObjectMapper() {
		var objectMapper = objectMapperConfig.objectMapper();

		assertNotNull(objectMapper);
		assertInstanceOf(ObjectMapper.class, objectMapper);
	}

}
