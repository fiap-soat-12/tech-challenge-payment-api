package br.com.fiap.techchallenge.payment.application.exceptions;

public class AlreadyInStatusException extends RuntimeException {

	public AlreadyInStatusException(String message) {
		super(message);
	}

}
