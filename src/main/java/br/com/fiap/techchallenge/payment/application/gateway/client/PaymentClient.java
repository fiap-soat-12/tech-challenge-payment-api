package br.com.fiap.techchallenge.payment.application.gateway.client;

import br.com.fiap.techchallenge.payment.infra.gateway.client.cotroller.dto.PaymentClientDTO;
import br.com.fiap.techchallenge.payment.infra.gateway.client.cotroller.dto.PaymentStatusClientDTO;

public interface PaymentClient {

	String generateQrCode(PaymentClientDTO dto);

	PaymentStatusClientDTO verifyPayment(String dataId);

}
