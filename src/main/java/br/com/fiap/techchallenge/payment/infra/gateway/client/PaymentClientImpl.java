package br.com.fiap.techchallenge.payment.infra.gateway.client;

import br.com.fiap.techchallenge.payment.application.gateway.client.PaymentClient;
import br.com.fiap.techchallenge.payment.infra.gateway.client.cotroller.dto.PaymentClientDTO;
import br.com.fiap.techchallenge.payment.infra.gateway.client.cotroller.dto.PaymentStatusClientDTO;
import br.com.fiap.techchallenge.payment.infra.gateway.client.cotroller.request.MpPaymentItemQRRequest;
import br.com.fiap.techchallenge.payment.infra.gateway.client.cotroller.request.MpPaymentQRRequest;
import br.com.fiap.techchallenge.payment.infra.gateway.client.cotroller.response.MpPaymentGetResponse;
import br.com.fiap.techchallenge.payment.infra.gateway.client.cotroller.response.MpPaymentQRResponse;
import br.com.fiap.techchallenge.payment.infra.gateway.client.cotroller.PaymentClientController;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static java.util.stream.Collectors.toList;

@Component
public class PaymentClientImpl implements PaymentClient {

	private final PaymentClientController paymentClientController;

	public PaymentClientImpl(PaymentClientController paymentClientController) {
		this.paymentClientController = paymentClientController;
	}

	@Override
	public String generateQrCode(PaymentClientDTO dto) {
		// var notificationUrl = getHostInfo() + "/v1/webhook-payment";
		var notificationUrl = "https://soat-app.free.beeceptor.com";

		var request = new MpPaymentQRRequest(dto.getDescription(), dto.getExpirationDate(), dto.getExternalReference(),
				dto.getItems()
					.stream()
					.map(item -> new MpPaymentItemQRRequest(item.getTitle(), item.getUnitPrice(), item.getQuantity(),
							item.getUnitMeasure(), item.getTotalAmount()))
					.collect(toList()),
				dto.getTitle(), dto.getTotalAmount(), notificationUrl);

		MpPaymentQRResponse response = paymentClientController.createQr(request);

		dto.setInStoreOrderId(response.getInStoreOrderId());
		dto.setQrData(response.getQrData());
		return dto.getQrData();
	}

	@Override
	public PaymentStatusClientDTO verifyPayment(String dataId) {
		MpPaymentGetResponse response = paymentClientController.getPayment(dataId);

		return new PaymentStatusClientDTO(response.getExternalReference(), response.getId(), response.getStatus());
	}

	private String getHostInfo() {
		return ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
	}

}
