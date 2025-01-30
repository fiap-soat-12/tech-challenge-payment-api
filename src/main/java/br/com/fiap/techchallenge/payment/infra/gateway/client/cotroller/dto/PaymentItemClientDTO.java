package br.com.fiap.techchallenge.payment.infra.gateway.client.cotroller.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PaymentItemClientDTO {

	private String title;

	private BigDecimal unitPrice;

	private Integer quantity;

	private String unitMeasure;

	private BigDecimal totalAmount;

	public PaymentItemClientDTO(BigDecimal amount) {
		this.title = "FIAP Payment";
		this.unitPrice = amount.setScale(2, RoundingMode.HALF_UP);
		this.quantity = 1;
		this.unitMeasure = "unit";
		this.totalAmount = amount;
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
