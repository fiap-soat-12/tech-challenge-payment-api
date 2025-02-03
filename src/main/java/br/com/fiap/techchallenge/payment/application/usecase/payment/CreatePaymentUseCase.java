package br.com.fiap.techchallenge.payment.application.usecase.payment;

import br.com.fiap.techchallenge.payment.application.usecase.payment.dto.PaymentCreateDTO;
import br.com.fiap.techchallenge.payment.domain.models.Payment;

public interface CreatePaymentUseCase {

	Payment create(PaymentCreateDTO input);

}
