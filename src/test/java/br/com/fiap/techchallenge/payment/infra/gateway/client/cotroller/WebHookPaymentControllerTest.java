package br.com.fiap.techchallenge.payment.infra.gateway.client.cotroller;

import br.com.fiap.techchallenge.payment.application.usecase.payment.UpdatePaymentPaidUseCase;
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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
class WebHookPaymentControllerTest {

	private MockMvc mockMvc;

	@Mock
	private UpdatePaymentPaidUseCase updatePaymentPaidUseCase;

	@InjectMocks
	private WebHookPaymentController webHookPaymentController;

	private final String baseUrl = "/v1/webhook-payment";

	private String id;

	private String topic;

	@BeforeEach
	void setUp() {
		mockMvc = standaloneSetup(webHookPaymentController).setControllerAdvice(new ControllerAdvice()).build();
		this.buildArranges();
	}

	@Test
	@DisplayName("Should Handle Webhook and Update Payment Status")
	void shouldHandleWebhookAndUpdatePaymentStatus() throws Exception {
		assertDoesNotThrow(() -> updatePaymentPaidUseCase.updatePaymentByDataId(anyString()));

		mockMvc.perform(post(baseUrl).param("id", id).param("topic", topic).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());

		verify(updatePaymentPaidUseCase, times(2)).updatePaymentByDataId(anyString());
	}

	private void buildArranges() {
		id = "87847869771";
		topic = "payment";
	}

}