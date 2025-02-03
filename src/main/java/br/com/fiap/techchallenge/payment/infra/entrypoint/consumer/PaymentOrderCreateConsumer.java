package br.com.fiap.techchallenge.payment.infra.entrypoint.consumer;

import br.com.fiap.techchallenge.payment.application.usecase.payment.CreatePaymentUseCase;
import br.com.fiap.techchallenge.payment.application.usecase.payment.dto.PaymentCreateDTO;
import br.com.fiap.techchallenge.payment.infra.entrypoint.consumer.dto.PaymentOrderCreateDTO;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentOrderCreateConsumer {

	private final CreatePaymentUseCase createPaymentUseCase;

	public PaymentOrderCreateConsumer(CreatePaymentUseCase createPaymentUseCase) {
		this.createPaymentUseCase = createPaymentUseCase;
	}

	@SqsListener("${sqs.queue.payment.order.create.consumer}")
	public void receiveMessage(PaymentOrderCreateDTO dto) {
		createPaymentUseCase.create(new PaymentCreateDTO(dto));
	}

}
