package br.com.fiap.techchallenge.payment.infra.gateway.producer;

import br.com.fiap.techchallenge.payment.infra.gateway.producer.dto.OrderStatusUpdateDTO;
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

	public OrderStatusUpdateProducer(SqsTemplate sqsTemplate) {
		this.sqsTemplate = sqsTemplate;
	}

	public void sendMessage(OrderStatusUpdateDTO dto) {
		log.info("Producing Order Status Update: {} - {}", dto.orderId(), dto.isPaid());
		sqsTemplate.send(queue, dto);
	}

}
