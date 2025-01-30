package br.com.fiap.techchallenge.payment.application.exceptions;

public class DoesNotExistException extends RuntimeException {

	public DoesNotExistException(String message) {
		super(message);
	}

}
