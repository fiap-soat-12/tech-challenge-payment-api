package br.com.fiap.techchallenge.payment.application.exceptions;

public class InvalidStatusUpdateException extends RuntimeException {

	public InvalidStatusUpdateException(String message) {
		super(message);
	}

}
