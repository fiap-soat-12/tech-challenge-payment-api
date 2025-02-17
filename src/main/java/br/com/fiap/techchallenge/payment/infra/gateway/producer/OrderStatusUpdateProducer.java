package br.com.fiap.techchallenge.payment.infra.gateway.producer;

import br.com.fiap.techchallenge.payment.application.exceptions.InvalidStatusUpdateException;
import br.com.fiap.techchallenge.payment.infra.gateway.producer.dto.OrderStatusUpdateDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusUpdateProducer {

	private static final Logger log = LoggerFactory.getLogger(OrderStatusUpdateProducer.class);

	@Value("${sqs.queue.order.status.update.producer}")
	private String queue;

	private final SqsTemplate sqsTemplate;

	private final ObjectMapper objectMapper;

	public OrderStatusUpdateProducer(SqsTemplate sqsTemplate, ObjectMapper objectMapper) {
		this.sqsTemplate = sqsTemplate;
		this.objectMapper = objectMapper;
	}

	public void sendMessage(OrderStatusUpdateDTO dto) {
		log.info("Producing Order Status Update: {} - {}", dto.orderId(), dto.isPaid());
		try {
			sqsTemplate.send(queue, objectMapper.writeValueAsString(dto));
		}
		catch (JsonProcessingException e) {
			throw new InvalidStatusUpdateException("It was not possible to post message in queue");
		}
	}

}
