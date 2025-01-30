package br.com.fiap.techchallenge.payment.infra.gateway.client.cotroller;

import br.com.fiap.techchallenge.payment.application.usecase.payment.UpdatePaymentPaidUseCase;
import br.com.fiap.techchallenge.payment.infra.entrypoint.controller.handler.ControllerAdvice;
import br.com.fiap.techchallenge.payment.infra.gateway.client.cotroller.request.WebHookPaymentDataRequest;
import br.com.fiap.techchallenge.payment.infra.gateway.client.cotroller.request.WebHookPaymentRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
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

	private String dataId;

	private String type;

	private WebHookPaymentRequest webHookPaymentRequest;

	@BeforeEach
	void setUp() {
		mockMvc = standaloneSetup(webHookPaymentController).setControllerAdvice(new ControllerAdvice()).build();
		this.buildArranges();
	}

	@Test
	@DisplayName("Should Handle Webhook and Update Payment Status")
	void shouldHandleWebhookAndUpdatePaymentStatus() throws Exception {
		assertDoesNotThrow(() -> updatePaymentPaidUseCase.updateStatusByPaymentDataId(anyString()));

		mockMvc
			.perform(post(baseUrl).param("data.id", dataId)
				.param("type", type)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(webHookPaymentRequest)))
			.andExpect(status().isNoContent());

		verify(updatePaymentPaidUseCase, times(2)).updateStatusByPaymentDataId(anyString());
	}

	@Test
	@DisplayName("Should Not Update Payment Status When Data ID or Type Does Not Match")
	void shouldNotUpdatePaymentStatusWhenDataIdOrTypeDoesNotMatch() throws Exception {
        dataId = "67847869771";

		mockMvc
			.perform(post(baseUrl).param("data.id", dataId)
				.param("type", type)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(webHookPaymentRequest)))
			.andExpect(status().isNoContent());

		verify(updatePaymentPaidUseCase, never()).updateStatusByPaymentDataId(anyString());
	}

	private void buildArranges() {
		dataId = "87847869771";
		type = "payment";
		webHookPaymentRequest = new WebHookPaymentRequest("payment.created", "v1",
				new WebHookPaymentDataRequest(dataId), "2024-09-17T23:29:26Z", 115915708642L, true, "payment",
				"1986357239");
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}