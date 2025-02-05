package br.com.fiap.techchallenge.payment.infra.entrypoint.controller.handler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.validation.FieldError;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorsValidateDataTest {

	@Test
	@DisplayName("Should Create ErrorsValidateData From FieldError")
	void shouldCreateErrorsValidateDataFromFieldError() {
		String fieldName = "fieldName";
		String errorMessage = "must not be blank";
		FieldError fieldError = new FieldError("objectName", fieldName, errorMessage);

		ErrorsValidateData errorsValidateData = new ErrorsValidateData(fieldError);

		assertEquals(fieldName, errorsValidateData.field());
		assertEquals(errorMessage, errorsValidateData.message());
	}

}
