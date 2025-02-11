package br.com.fiap.techchallenge.payment.infra.entrypoint.consumer;

import br.com.fiap.techchallenge.payment.application.usecase.payment.CreatePaymentUseCase;
import br.com.fiap.techchallenge.payment.application.usecase.payment.dto.PaymentCreateDTO;
import br.com.fiap.techchallenge.payment.infra.entrypoint.consumer.dto.PaymentOrderCreateDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PaymentOrderCreateConsumerTest {

	@Mock
	private CreatePaymentUseCase createPaymentUseCase;

	private PaymentOrderCreateConsumer paymentOrderCreateConsumer;

	private PaymentOrderCreateDTO paymentOrderCreateDTO;

	@BeforeEach
	public void setUp() {
		paymentOrderCreateConsumer = new PaymentOrderCreateConsumer(createPaymentUseCase);
		this.buildArranges();
	}

	@Test
	@DisplayName("Should Receive Message Of Payment Order Create")
	void shouldReceiveMessageOfPaymentOrderCreateDTO() {
		assertDoesNotThrow(() -> paymentOrderCreateConsumer.receiveMessage(paymentOrderCreateDTO));

		verify(createPaymentUseCase).create(any(PaymentCreateDTO.class));
	}

	private void buildArranges() {
		var orderId = UUID.randomUUID();
		var amount = new BigDecimal("100.00");

		paymentOrderCreateDTO = new PaymentOrderCreateDTO(orderId, amount);
	}

}
