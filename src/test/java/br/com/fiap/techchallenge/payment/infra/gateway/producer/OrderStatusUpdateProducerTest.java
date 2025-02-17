package br.com.fiap.techchallenge.payment.infra.gateway.producer;

import br.com.fiap.techchallenge.payment.application.exceptions.InvalidStatusUpdateException;
import br.com.fiap.techchallenge.payment.infra.gateway.producer.dto.OrderStatusUpdateDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.operations.SendResult;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(MockitoExtension.class)
class OrderStatusUpdateProducerTest {

	@Mock
	private SqsTemplate sqsTemplate;

	@Mock
	private ObjectMapper objectMapper;

	@Mock
	private SendResult<OrderStatusUpdateDTO> sendResult;

	private OrderStatusUpdateProducer orderStatusUpdateProducer;

	private OrderStatusUpdateDTO orderStatusUpdateDTO;

	@BeforeEach
	public void setUp() {
		orderStatusUpdateProducer = new OrderStatusUpdateProducer(sqsTemplate, objectMapper);
		setField(orderStatusUpdateProducer, "queue", "test-queue");
		this.buildArranges();
	}

	@Test
	@DisplayName("Should Send Message Of Order Status Update")
	void ShouldSendMessageOfOrderStatusUpdate() throws JsonProcessingException {
		orderStatusUpdateProducer.sendMessage(orderStatusUpdateDTO);

		verify(sqsTemplate).send("test-queue", objectMapper.writeValueAsString(orderStatusUpdateDTO));
		verify(objectMapper, times(2)).writeValueAsString(orderStatusUpdateDTO);
	}

	@Test
	void sendMessage_JsonProcessingException() throws JsonProcessingException {
		var dto = new OrderStatusUpdateDTO(UUID.randomUUID(), true);

		when(objectMapper.writeValueAsString(dto)).thenThrow(JsonProcessingException.class);

		try {
			orderStatusUpdateProducer.sendMessage(dto);
		}
		catch (InvalidStatusUpdateException e) {
			// DONothing
		}

		verify(sqsTemplate, never()).send(anyString(), anyString());
	}

	private void buildArranges() {
		orderStatusUpdateDTO = new OrderStatusUpdateDTO(UUID.randomUUID(), true);
	}

}