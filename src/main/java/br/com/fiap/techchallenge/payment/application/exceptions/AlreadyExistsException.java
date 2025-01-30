package br.com.fiap.techchallenge.payment.application.exceptions;

public class AlreadyExistsException extends RuntimeException {

	public AlreadyExistsException(String message) {
		super(message);
	}

}
