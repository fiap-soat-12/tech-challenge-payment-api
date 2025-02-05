package br.com.fiap.techchallenge.payment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PaymentApplicationTest {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	@DisplayName("Should Context Loads")
	void contextLoads() {
		assertNotNull(applicationContext);
	}

	@Test
	@DisplayName("Should Main Run")
	void shouldMainRun() {
		assertDoesNotThrow(() -> PaymentApplication.main(new String[] {}));
	}

}