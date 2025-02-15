package br.com.fiap.techchallenge.payment.infra.entrypoint.consumer;

import br.com.fiap.techchallenge.payment.application.usecase.payment.CreatePaymentUseCase;
import br.com.fiap.techchallenge.payment.application.usecase.payment.dto.PaymentCreateDTO;
import br.com.fiap.techchallenge.payment.infra.entrypoint.consumer.dto.PaymentOrderCreateDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PaymentOrderCreateConsumer {

	private static final Logger log = LoggerFactory.getLogger(PaymentOrderCreateConsumer.class);

	private final CreatePaymentUseCase createPaymentUseCase;

	private final ObjectMapper objectMapper;

	public PaymentOrderCreateConsumer(CreatePaymentUseCase createPaymentUseCase, ObjectMapper objectMapper) {
		this.createPaymentUseCase = createPaymentUseCase;
		this.objectMapper = objectMapper;
	}

	@SqsListener("${sqs.queue.payment.order.create.consumer}")
	public void receiveMessage(String message) throws JsonProcessingException {
		log.info("Received Payment Order Create: {}", message);
		createPaymentUseCase.create(new PaymentCreateDTO(objectMapper.readValue(message, PaymentOrderCreateDTO.class)));
	}

}