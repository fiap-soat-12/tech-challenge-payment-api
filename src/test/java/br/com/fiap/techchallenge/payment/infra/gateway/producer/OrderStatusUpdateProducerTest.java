package br.com.fiap.techchallenge.payment.infra.gateway.producer;

import br.com.fiap.techchallenge.payment.infra.gateway.producer.dto.OrderStatusUpdateDTO;
import io.awspring.cloud.sqs.operations.SendResult;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(MockitoExtension.class)
class OrderStatusUpdateProducerTest {

	@Mock
	private SqsTemplate sqsTemplate;

	@Mock
	private SendResult<OrderStatusUpdateDTO> sendResult;

	private OrderStatusUpdateProducer orderStatusUpdateProducer;

	private OrderStatusUpdateDTO orderStatusUpdateDTO;

	@BeforeEach
	public void setUp() {
		orderStatusUpdateProducer = new OrderStatusUpdateProducer(sqsTemplate);
		setField(orderStatusUpdateProducer, "queue", "test-queue");
		this.buildArranges();
	}

	@Test
	@DisplayName("Should Send Message Of Order Status Update")
	void ShouldSendMessageOfOrderStatusUpdate() {
		when(sqsTemplate.send(anyString(), any(OrderStatusUpdateDTO.class))).thenReturn(sendResult);

		assertDoesNotThrow(() -> orderStatusUpdateProducer.sendMessage(orderStatusUpdateDTO));

		verify(sqsTemplate).send(anyString(), any(OrderStatusUpdateDTO.class));
	}

	@Test
	@DisplayName("Should Not Send Message Of Order Status Update")
	void ShouldNotSendMessageOfOrderStatusUpdate() {
		assertDoesNotThrow(() -> orderStatusUpdateProducer.sendMessage(null));

		verify(sqsTemplate, never()).send(anyString(), any(OrderStatusUpdateDTO.class));
	}

	private void buildArranges() {
		orderStatusUpdateDTO = new OrderStatusUpdateDTO(UUID.randomUUID(), true);
	}

}