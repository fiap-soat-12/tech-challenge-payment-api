package br.com.fiap.techchallenge.payment.infra.entrypoint.consumer;

import br.com.fiap.techchallenge.payment.application.usecase.payment.CreatePaymentUseCase;
import br.com.fiap.techchallenge.payment.application.usecase.payment.dto.PaymentCreateDTO;
import br.com.fiap.techchallenge.payment.domain.models.Payment;
import br.com.fiap.techchallenge.payment.infra.entrypoint.consumer.dto.PaymentOrderCreateDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentOrderCreateConsumerTest {

	@Mock
	private CreatePaymentUseCase createPaymentUseCase;

	private PaymentOrderCreateConsumer paymentOrderCreateConsumer;

	private PaymentOrderCreateDTO paymentOrderCreateDTO;

	private Payment payment;

	@BeforeEach
	public void setUp() {
		paymentOrderCreateConsumer = new PaymentOrderCreateConsumer(createPaymentUseCase);
		this.buildArranges();
	}

	@Test
	@DisplayName("Should Receive Message Of Payment Order Create")
	void shouldReceiveMessageOfPaymentOrderCreateDTO(){
        when(createPaymentUseCase.create(any(PaymentCreateDTO.class))).thenReturn(payment);

        assertDoesNotThrow(() -> paymentOrderCreateConsumer.receiveMessage(paymentOrderCreateDTO));

		verify(createPaymentUseCase).create(any(PaymentCreateDTO.class));
	}

	private void buildArranges() {
		var orderId = UUID.randomUUID();
		var amount = new BigDecimal("100.00");

		paymentOrderCreateDTO = new PaymentOrderCreateDTO(orderId, amount);
		payment = new Payment(UUID.randomUUID(), amount, false, UUID.randomUUID(),
				"00020126580014br.gov.bcb.pix0136123e4567-e12b-12d1-a456-426655440000 5204000053039865802BR5913Fulano de Tal6008BRASILIA62070503***63041D3D",
				orderId, LocalDateTime.now(), LocalDateTime.now());
	}

}
