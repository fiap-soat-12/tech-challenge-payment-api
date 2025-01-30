package br.com.fiap.techchallenge.payment.application.usecase.payment;

public interface UpdatePaymentPaidUseCase {

	void updateStatusByPaymentDataId(String paymentDataId);

	void updateOrderStatus();

}
