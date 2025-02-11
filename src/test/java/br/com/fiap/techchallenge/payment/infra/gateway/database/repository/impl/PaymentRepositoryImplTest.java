package br.com.fiap.techchallenge.payment.infra.gateway.database.repository.impl;

import br.com.fiap.techchallenge.payment.domain.models.Payment;
import br.com.fiap.techchallenge.payment.infra.gateway.database.entities.PaymentEntity;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentRepositoryImplTest {

	@Mock
	private DynamoDbTemplate dynamoDbTemplate;

	@InjectMocks
	private PaymentRepositoryImpl paymentRepository;

	private PaymentEntity paymentEntity;

	@BeforeEach
	void setUp() {
		this.buildArranges();
	}

	@Test
	@DisplayName("Should save Payment Entity")
	void shouldSavePaymentEntity() {
		when(dynamoDbTemplate.save(any(PaymentEntity.class))).thenReturn(paymentEntity);

		var result = paymentRepository.save(paymentEntity);

		assertNotNull(result);
		assertEquals(paymentEntity, result);
		verify(dynamoDbTemplate).save(paymentEntity);
	}

	@Test
	@DisplayName("Should return payment if exists when search for order id")
	void shouldReturnPaymentIfExistsWhenSearchForOrderId() {
		var orderId = paymentEntity.getOrderId();
		var page = Page.create(List.of(paymentEntity));
		SdkIterable<Page<PaymentEntity>> paymentPage = () -> List.of(page).iterator();
		var paymentPageIterable = PageIterable.create(paymentPage);

		when(dynamoDbTemplate.scan(any(ScanEnhancedRequest.class), eq(PaymentEntity.class)))
			.thenReturn(paymentPageIterable);

		var result = paymentRepository.findByOrderId(orderId);

		assertTrue(result.isPresent());
		assertEquals(orderId, result.get().getOrderId());
		verify(dynamoDbTemplate).scan(any(ScanEnhancedRequest.class), eq(PaymentEntity.class));
	}

	@Test
	@DisplayName("Should return payment empty if not exists when search for order id")
	void shouldReturnPaymentEmptyIfNotExistsWhenSearchForOrderId() {
		var orderId = paymentEntity.getOrderId();
		Page<PaymentEntity> page = Page.create(new ArrayList<>());
		SdkIterable<Page<PaymentEntity>> paymentPage = () -> List.of(page).iterator();
		PageIterable<PaymentEntity> paymentPageIterable = PageIterable.create(paymentPage);

		when(dynamoDbTemplate.scan(any(ScanEnhancedRequest.class), eq(PaymentEntity.class)))
			.thenReturn(paymentPageIterable);

		var result = paymentRepository.findByOrderId(orderId);

		assertFalse(result.isPresent());
		verify(dynamoDbTemplate).scan(any(ScanEnhancedRequest.class), eq(PaymentEntity.class));
	}

	@Test
	@DisplayName("Should return payment if exists when search for external payment id")
	void shouldReturnPaymentIfExistsWhenSearchForExternalPaymentId() {
		var orderId = paymentEntity.getOrderId();
		var page = Page.create(List.of(paymentEntity));
		SdkIterable<Page<PaymentEntity>> paymentPage = () -> List.of(page).iterator();
		var paymentPageIterable = PageIterable.create(paymentPage);

		when(dynamoDbTemplate.scan(any(ScanEnhancedRequest.class), eq(PaymentEntity.class)))
			.thenReturn(paymentPageIterable);

		var result = paymentRepository.findByExternalPaymentId(orderId);

		assertTrue(result.isPresent());
		assertEquals(orderId, result.get().getOrderId());
		verify(dynamoDbTemplate).scan(any(ScanEnhancedRequest.class), eq(PaymentEntity.class));
	}

	@Test
	@DisplayName("Should return payment empty if not exists when search for external payment id")
	void shouldReturnPaymentEmptyIfExistsWhenSearchForExternalPaymentId() {
		var orderId = paymentEntity.getOrderId();
		Page<PaymentEntity> page = Page.create(new ArrayList<>());
		SdkIterable<Page<PaymentEntity>> paymentPage = () -> List.of(page).iterator();
		PageIterable<PaymentEntity> paymentPageIterable = PageIterable.create(paymentPage);

		when(dynamoDbTemplate.scan(any(ScanEnhancedRequest.class), eq(PaymentEntity.class)))
			.thenReturn(paymentPageIterable);

		var result = paymentRepository.findByExternalPaymentId(orderId);

		assertFalse(result.isPresent());
		verify(dynamoDbTemplate).scan(any(ScanEnhancedRequest.class), eq(PaymentEntity.class));
	}

	@Test
	@DisplayName("Should return list of payments when the pain is false and createdAt is Less than now")
	void shouldReturnListOfPaymentsWhenThePainIsFalseAndCreatedAtIsLessThanNow() {
		var createdAt = paymentEntity.getCreatedAt();
		var page = Page.create(List.of(paymentEntity, paymentEntity));
		SdkIterable<Page<PaymentEntity>> paymentPage = () -> List.of(page).iterator();
		var paymentPageIterable = PageIterable.create(paymentPage);

		when(dynamoDbTemplate.scan(any(ScanEnhancedRequest.class), eq(PaymentEntity.class)))
			.thenReturn(paymentPageIterable);

		var result = paymentRepository.findByPaidIsNullAndCreatedAtBefore(createdAt);

		assertNotNull(result);
		assertEquals(2, result.size());
		verify(dynamoDbTemplate).scan(any(ScanEnhancedRequest.class), eq(PaymentEntity.class));
	}

	@Test
	@DisplayName("Should return list of payments empty when the pain is false and createdAt is Less than now")
	void shouldReturnListOfPaymentsEmptyWhenThePainIsFalseAndCreatedAtIsLessThanNow() {
		var createdAt = paymentEntity.getCreatedAt();
		Page<PaymentEntity> page = Page.create(new ArrayList<>());
		SdkIterable<Page<PaymentEntity>> paymentPage = () -> List.of(page).iterator();
		var paymentPageIterable = PageIterable.create(paymentPage);

		when(dynamoDbTemplate.scan(any(ScanEnhancedRequest.class), eq(PaymentEntity.class)))
			.thenReturn(paymentPageIterable);

		var result = paymentRepository.findByPaidIsNullAndCreatedAtBefore(createdAt);

		assertTrue(result.isEmpty());
		verify(dynamoDbTemplate).scan(any(ScanEnhancedRequest.class), eq(PaymentEntity.class));
	}

	private void buildArranges() {
		paymentEntity = new PaymentEntity(new Payment(UUID.randomUUID(), new BigDecimal("100.00"), false,
				UUID.randomUUID(), "QR Code", UUID.randomUUID(), LocalDateTime.now(), LocalDateTime.now()));
	}

}