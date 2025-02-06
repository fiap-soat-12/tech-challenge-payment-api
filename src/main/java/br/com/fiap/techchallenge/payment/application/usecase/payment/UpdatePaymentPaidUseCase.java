package br.com.fiap.techchallenge.payment.application.usecase.payment;

public interface UpdatePaymentPaidUseCase {

	void updatePaymentByDataId(String paymentDataId);

	void updateOrderStatus();

}
