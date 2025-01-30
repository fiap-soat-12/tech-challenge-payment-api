package br.com.fiap.techchallenge.payment.infra.gateway.client.cotroller.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class MpPaymentItemQRRequest {

	private String title;

	@JsonProperty("unit_price")
	private BigDecimal unitPrice;

	private Integer quantity;

	@JsonProperty("unit_measure")
	private String unitMeasure;

	@JsonProperty("total_amount")
	private BigDecimal totalAmount;

	public MpPaymentItemQRRequest(String title, BigDecimal unitPrice, Integer quantity, String unitMeasure,
			BigDecimal totalAmount) {
		this.title = title;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
		this.unitMeasure = unitMeasure;
		this.totalAmount = totalAmount;
	}

	public String getTitle() {
		return title;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public String getUnitMeasure() {
		return unitMeasure;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

}
