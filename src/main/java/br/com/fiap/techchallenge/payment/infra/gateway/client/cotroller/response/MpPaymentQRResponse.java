package br.com.fiap.techchallenge.payment.infra.gateway.client.cotroller.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MpPaymentQRResponse {

	@JsonProperty("in_store_order_id")
	private String inStoreOrderId;

	@JsonProperty("qr_data")
	private String qrData;

	public MpPaymentQRResponse() {
	}

	public MpPaymentQRResponse(String inStoreOrderId, String qrData) {
		this.inStoreOrderId = inStoreOrderId;
		this.qrData = qrData;
	}

	public String getInStoreOrderId() {
		return inStoreOrderId;
	}

	public String getQrData() {
		return qrData;
	}

}
