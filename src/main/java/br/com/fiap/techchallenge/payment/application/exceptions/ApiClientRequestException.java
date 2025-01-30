package br.com.fiap.techchallenge.payment.application.exceptions;

public class ApiClientRequestException extends RuntimeException {

	public ApiClientRequestException(String message) {
		super(message);
	}

}