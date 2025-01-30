package br.com.fiap.techchallenge.payment.infra.entrypoint.controller;

import br.com.fiap.techchallenge.payment.application.exceptions.DoesNotExistException;
import br.com.fiap.techchallenge.payment.application.usecase.payment.FindPaymentQrByOrderIdUseCase;
import br.com.fiap.techchallenge.payment.domain.models.Payment;
import br.com.fiap.techchallenge.payment.infra.entrypoint.controller.dto.PaymentQrResponseDTO;
import br.com.fiap.techchallenge.payment.infra.entrypoint.controller.handler.ControllerAdvice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
class PaymentQrControllerTest {

	private MockMvc mockMvc;

	@Mock
	private FindPaymentQrByOrderIdUseCase findPaymentQrByOrderIdUseCase;

	@InjectMocks
	private PaymentQrController paymentQrController;

	private final String baseUrl = "/v1/qrs";

	private Payment payment;

	private PaymentQrResponseDTO paymentQrResponseDTO;

	@BeforeEach
	public void setUp() {
		mockMvc = standaloneSetup(paymentQrController).setControllerAdvice(new ControllerAdvice()).build();
		this.buildArranges();
	}

	@Test
	@DisplayName("Should Find QR by Order ID")
	void shouldFindQrByOrderId() throws Exception {
		when(findPaymentQrByOrderIdUseCase.findQrByOrderId(any(UUID.class))).thenReturn(payment);

		mockMvc.perform(get(baseUrl.concat("/{orderId}"), payment.getOrderId())
                        .accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.qr").value(paymentQrResponseDTO.qr()));

		verify(findPaymentQrByOrderIdUseCase).findQrByOrderId(any(UUID.class));
	}

	@Test
    @DisplayName("Should Return Not Found When Qr Does Not Exist")
    void shouldReturnNotFoundWhenQrDoesNotExist() throws Exception {
        when(findPaymentQrByOrderIdUseCase.findQrByOrderId(any(UUID.class))).thenThrow(DoesNotExistException.class);

        mockMvc.perform(get(baseUrl.concat("/{orderId}"), payment.getOrderId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(findPaymentQrByOrderIdUseCase).findQrByOrderId(any(UUID.class));
    }

	private void buildArranges() {
		payment = new Payment(UUID.randomUUID(), new BigDecimal("100.00"), true, UUID.randomUUID(),
				"00020126580014br.gov.bcb.pix0136123e4567-e12b-12d1-a456-426655440000 5204000053039865802BR5913Fulano de Tal6008BRASILIA62070503***63041D3D",
				UUID.randomUUID(), LocalDateTime.now(), LocalDateTime.now());
		paymentQrResponseDTO = new PaymentQrResponseDTO(payment.getQr());
	}

}
