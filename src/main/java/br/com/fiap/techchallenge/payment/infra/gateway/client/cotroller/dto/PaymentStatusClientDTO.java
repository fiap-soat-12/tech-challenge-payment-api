package br.com.fiap.techchallenge.payment.infra.gateway.client.cotroller.dto;

public class PaymentStatusClientDTO {

	private String externalReference;

	private Long id;

	private String status;

	public PaymentStatusClientDTO(String externalReference, Long id, String status) {
		this.externalReference = externalReference;
		this.id = id;
		this.status = status;
	}

	public String getExternalReference() {
		return externalReference;
	}

	public Long getId() {
		return id;
	}

	public String getStatus() {
		return status;
	}

}
