package br.com.fiap.techchallenge.payment.application.usecase.payment;

import br.com.fiap.techchallenge.payment.application.usecase.payment.dto.PaymentCreateDTO;

public interface CreatePaymentUseCase {

	void create(PaymentCreateDTO input);

}
