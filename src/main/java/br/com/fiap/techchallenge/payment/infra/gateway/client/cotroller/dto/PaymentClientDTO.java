package br.com.fiap.techchallenge.payment.infra.gateway.client.cotroller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public class PaymentClientDTO {

	private String description;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
	private OffsetDateTime expirationDate;

	private UUID externalReference;

	private List<PaymentItemClientDTO> items;

	private String title;

	private BigDecimal totalAmount;

	private String inStoreOrderId;

	private String qrData;

	public PaymentClientDTO(List<PaymentItemClientDTO> items, BigDecimal totalAmount, UUID externalReferenced) {
		this.items = items;
		this.totalAmount = totalAmount;
		this.externalReference = externalReferenced;
		this.description = "FIAP Payment";
		this.title = "FIAP Payment";
		this.expirationDate = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).plusMinutes(30).toOffsetDateTime();
	}

	public String getDescription() {
		return description;
	}

	public OffsetDateTime getExpirationDate() {
		return expirationDate;
	}

	public UUID getExternalReference() {
		return externalReference;
	}

	public List<PaymentItemClientDTO> getItems() {
		return items;
	}

	public String getTitle() {
		return title;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public String getInStoreOrderId() {
		return inStoreOrderId;
	}

	public String getQrData() {
		return qrData;
	}

	public void setQrData(String qrData) {
		this.qrData = qrData;
	}

	public void setInStoreOrderId(String inStoreOrderId) {
		this.inStoreOrderId = inStoreOrderId;
	}

}
