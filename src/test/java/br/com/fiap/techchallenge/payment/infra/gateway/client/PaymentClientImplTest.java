package br.com.fiap.techchallenge.payment.infra.gateway.client;

import br.com.fiap.techchallenge.payment.infra.gateway.client.cotroller.PaymentClientController;
import br.com.fiap.techchallenge.payment.infra.gateway.client.cotroller.dto.PaymentClientDTO;
import br.com.fiap.techchallenge.payment.infra.gateway.client.cotroller.dto.PaymentItemClientDTO;
import br.com.fiap.techchallenge.payment.infra.gateway.client.cotroller.dto.PaymentStatusClientDTO;
import br.com.fiap.techchallenge.payment.infra.gateway.client.cotroller.request.MpPaymentQRRequest;
import br.com.fiap.techchallenge.payment.infra.gateway.client.cotroller.response.MpPaymentGetResponse;
import br.com.fiap.techchallenge.payment.infra.gateway.client.cotroller.response.MpPaymentQRResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentClientImplTest {

	@Mock
	private ServletUriComponentsBuilder builder;

	@Mock
	private PaymentClientController paymentClientController;

	private PaymentClientImpl paymentClientImpl;

	private PaymentClientDTO paymentClientDTO;

	private MpPaymentQRResponse mpPaymentQRResponse;

	private MpPaymentGetResponse mpPaymentGetResponse;

	private String dataId;

	private String storeId;

	private String qrData;

	@BeforeEach
	void setUp() {
		paymentClientImpl = new PaymentClientImpl(paymentClientController);
		this.buildArranges();
	}

	@Test
	@DisplayName("Should Generate QrCode Successfully")
	void shouldGenerateQrCodeSuccessfully() {
		when(paymentClientController.createQr(any(MpPaymentQRRequest.class))).thenReturn(mpPaymentQRResponse);

		String result = paymentClientImpl.generateQrCode(paymentClientDTO);

		assertNotNull(qrData);
		assertEquals(qrData, result);
		assertEquals(storeId, paymentClientDTO.getInStoreOrderId());
		verify(paymentClientController).createQr(any(MpPaymentQRRequest.class));
	}

	@Test
	@DisplayName("Should Verify Payment Successfully")
    void shouldVerifyPaymentSuccessfully() {
        when(paymentClientController.getPayment(dataId)).thenReturn(mpPaymentGetResponse);

        PaymentStatusClientDTO status = paymentClientImpl.verifyPayment(dataId);

        assertNotNull(status);
        assertEquals(mpPaymentGetResponse.getExternalReference(), status.getExternalReference());
        assertEquals(mpPaymentGetResponse.getId(), status.getId());
        assertEquals(mpPaymentGetResponse.getStatus(), status.getStatus());
        verify(paymentClientController).getPayment(dataId);
    }

	private void buildArranges() {
		dataId = UUID.randomUUID().toString();
		storeId = "store_id";
		qrData = "00020126580014br.gov.bcb.pix0136123e4567-e12b-12d1-a456-426655440000 5204000053039865802BR5913Fulano de Tal6008BRASILIA62070503***63041D3D";

		paymentClientDTO = new PaymentClientDTO(List.of(new PaymentItemClientDTO(new BigDecimal("10.00"))),
				new BigDecimal("10.00"), UUID.randomUUID());

		mpPaymentQRResponse = new MpPaymentQRResponse(storeId, qrData);
		mpPaymentGetResponse = new MpPaymentGetResponse("2023-10-23T12:34:56Z", "2023-09-13T09:12:34Z",
				"2023-10-28T15:45:12Z", "description", UUID.randomUUID().toString(), 123456L, "payment_method_id",
				"payment_type_id", "approved", "approved", storeId, 100.0);
	}

}
