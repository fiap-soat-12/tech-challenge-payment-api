package br.com.fiap.techchallenge.payment.infra.config.bean;

import br.com.fiap.techchallenge.payment.application.persistence.PaymentPersistence;
import br.com.fiap.techchallenge.payment.application.usecase.payment.impl.FindPaymentQrByOrderIdUseCaseImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class FindPaymentQrByOrderIdUseCaseConfigTest {

	@Mock
	PaymentPersistence persistence;

	@InjectMocks
	private FindPaymentQrByOrderIdUseCaseConfig findPaymentQrByOrderIdUseCaseConfig;

	@Test
	@DisplayName("Should Create a Singleton Instance Of FindPaymentQrByOrderIdUseCaseImpl")
	void shouldCreateSingletonInstanceOfFindPaymentQrByOrderIdUseCaseImpl() {
		var findQrByOrderIdUseCaseImpl = findPaymentQrByOrderIdUseCaseConfig.findPaymentQrByOrderIdUseCase(persistence);

		assertNotNull(findQrByOrderIdUseCaseImpl);
		assertNotNull(persistence);
		assertInstanceOf(FindPaymentQrByOrderIdUseCaseImpl.class, findQrByOrderIdUseCaseImpl);
	}

}
