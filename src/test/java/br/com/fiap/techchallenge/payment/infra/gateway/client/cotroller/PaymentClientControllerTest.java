package br.com.fiap.techchallenge.payment.infra.gateway.client.cotroller;

import br.com.fiap.techchallenge.payment.application.exceptions.ApiClientRequestException;
import br.com.fiap.techchallenge.payment.infra.gateway.client.cotroller.request.MpPaymentItemQRRequest;
import br.com.fiap.techchallenge.payment.infra.gateway.client.cotroller.request.MpPaymentQRRequest;
import br.com.fiap.techchallenge.payment.infra.gateway.client.cotroller.response.MpPaymentGetResponse;
import br.com.fiap.techchallenge.payment.infra.gateway.client.cotroller.response.MpPaymentQRResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PaymentClientControllerTest {

	private MockWebServer mockWebServer;

	private PaymentClientController paymentClientController;

	private final ObjectMapper objectMapper = new ObjectMapper();

	private Long dataId;

	private MpPaymentQRRequest mpPaymentQRRequest;

	private MpPaymentQRResponse mpPaymentQRResponse;

	private MpPaymentGetResponse mpPaymentGetResponse;

	@BeforeAll
	void startMockServer() throws IOException {
		mockWebServer = new MockWebServer();
		mockWebServer.start();
	}

	@BeforeEach
	void setUp() {
		URI baseUri = URI.create(mockWebServer.url("/").toString());
		HttpClient httpClient = HttpClient.newHttpClient();
		paymentClientController = new PaymentClientController(baseUri, httpClient, objectMapper);

		setField(paymentClientController, "TOKEN", "test-token");
		setField(paymentClientController, "CREATE_QR_URL", "create-qr");
		setField(paymentClientController, "GET_PAYMENT_URL", "get-payment/");
		setField(paymentClientController, "baseUri", baseUri);

		this.buildArranges();
	}

	@AfterAll
	void stopMockServer() throws IOException {
		mockWebServer.shutdown();
	}

	@Test
	@DisplayName("Should Create QR Code Successfully")
	void ShouldCreateQRCodeSuccessfully() throws Exception {
		mockWebServer.enqueue(new MockResponse().setResponseCode(201)
			.setBody(objectMapper.writeValueAsString(mpPaymentQRResponse))
			.addHeader("Content-Type", "application/json"));

		MpPaymentQRResponse response = paymentClientController.createQr(mpPaymentQRRequest);

		assertNotNull(response);
		var recordedRequest = mockWebServer.takeRequest();
		assertEquals("POST", recordedRequest.getMethod());
		assertEquals("/create-qr", recordedRequest.getPath());
		assertEquals("application/json", recordedRequest.getHeader("Content-Type"));
	}

	@Test
	@DisplayName("Should Get Payment Successfully")
	void ShouldGetPaymentSuccessfully() throws Exception {
		mockWebServer.enqueue(new MockResponse().setResponseCode(200)
			.setBody(objectMapper.writeValueAsString(mpPaymentGetResponse))
			.addHeader("Content-Type", "application/json"));

		MpPaymentGetResponse response = paymentClientController.getPayment(dataId.toString());

		assertNotNull(response);
		assertEquals(dataId, response.getId());
		var recordedRequest = mockWebServer.takeRequest();
		assertEquals("GET", recordedRequest.getMethod());
		assertEquals("/get-payment/" + dataId, recordedRequest.getPath());
	}

	@Test
	@DisplayName("Should Throw Exception When Data ID Is Null or Empty")
	void ShouldThrowExceptionWhenDataIdIsNullOrEmpty() {
		assertThrows(ApiClientRequestException.class, () -> paymentClientController.getPayment(null));
		assertThrows(ApiClientRequestException.class, () -> paymentClientController.getPayment(""));
	}

	@Test
	@DisplayName("Should Throw Exception When Status Code is Not 201 During QR Code Creation")
	void ShouldThrowExceptionWhenStatusCodeIsNot201DuringQRCodeCreation() throws Exception {
		mockWebServer.enqueue(new MockResponse().setResponseCode(400)
			.setBody("Bad Request")
			.addHeader("Content-Type", "application/json"));

		ApiClientRequestException exception = assertThrows(ApiClientRequestException.class,
				() -> paymentClientController.createQr(mpPaymentQRRequest));

		assertEquals("Request error: Status code 400", exception.getMessage());

		var recordedRequest = mockWebServer.takeRequest();
		assertEquals("POST", recordedRequest.getMethod());
		assertEquals("/create-qr", recordedRequest.getPath());
		assertEquals("application/json", recordedRequest.getHeader("Content-Type"));
	}

	@Test
	@DisplayName("Should Throw Exception When Status Code is Not 200 During Get Payment")
	void ShouldThrowExceptionWhenStatusCodeIsNot200DuringGetPayment() throws Exception {
		mockWebServer.enqueue(new MockResponse().setResponseCode(500)
			.setBody("Internal Server Error")
			.addHeader("Content-Type", "application/json"));

		ApiClientRequestException exception = assertThrowsExactly(ApiClientRequestException.class,
				() -> paymentClientController.getPayment(dataId.toString()));

		assertEquals("Request error: Status code 500", exception.getMessage());

		var recordedRequest = mockWebServer.takeRequest();
		assertEquals("GET", recordedRequest.getMethod());
		assertEquals("/get-payment/" + dataId, recordedRequest.getPath());
		assertEquals("application/json", recordedRequest.getHeader("Content-Type"));
	}

	@Test
	@DisplayName("Should Handle IOException and InterruptedException Correctly")
	void ShouldHandleIOExceptionAndInterruptedExceptionCorrectly() throws IOException, InterruptedException {
		HttpClient httpClientMock = mock(HttpClient.class);
		when(httpClientMock.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
			.thenThrow(new IOException("Network error"));

		paymentClientController = new PaymentClientController(URI.create("http://localhost"), httpClientMock,
				objectMapper);
		setField(paymentClientController, "TOKEN", "test-token");

		ApiClientRequestException exception = assertThrows(ApiClientRequestException.class,
				() -> paymentClientController.getPayment("123"));

		assertEquals("Error executing request: Network error", exception.getMessage());
	}

	private void buildArranges() {
		var externalReference = UUID.randomUUID();
		var title = "FIAP Payment";
		var description = "FIAP Payment";
		var amount = new BigDecimal("100.00");
		dataId = 123456789L;

		mpPaymentQRRequest = new MpPaymentQRRequest(description,
				ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).plusMinutes(30).toOffsetDateTime(), externalReference,
				List.of(new MpPaymentItemQRRequest(title, amount.setScale(2, RoundingMode.HALF_UP), 1, "unit", amount)),
				title, new BigDecimal("100.00"), "http://localhost:8080/v1/webhook-payment");

		mpPaymentQRResponse = new MpPaymentQRResponse("store_id",
				"00020126580014br.gov.bcb.pix0136123e4567-e12b-12d1-a456-426655440000 5204000053039865802BR5913Fulano de Tal6008BRASILIA62070503***63041D3D");

		mpPaymentGetResponse = new MpPaymentGetResponse("2023-10-23T12:34:56Z", "2023-09-13T09:12:34Z",
				"2023-10-28T15:45:12Z", description, externalReference.toString(), dataId, "payment_method_id",
				"payment_type_id", "approved", "approved", "store_id", amount.doubleValue());
	}

}
