package com.team1.goorm.service;

import com.team1.goorm.domain.dto.OrderPreviewRequestDto;
import com.team1.goorm.domain.dto.OrderPreviewResponseDto;
import com.team1.goorm.domain.dto.PaymentRequestDto;
import com.team1.goorm.domain.dto.PaymentResponseDto;
import com.team1.goorm.domain.entity.*;
import com.team1.goorm.repository.OrderRepository;
import com.team1.goorm.repository.PaymentRepository;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    public OrderPreviewResponseDto createOrder(OrderPreviewRequestDto requestDto, User user) {
        // 주문 ID를 받아오기 위해 임시 저장
        Order order = Order.builder()
                .user(user)
                .status(OrderStatus.READY)
                .createdAt(LocalDateTime.now())
                .orderNumber("temp_id")
                .build();
        Order savedOrder = orderRepository.save(order);

        // 주문의 상품들 ID를 리스트로 만들기
        List<Long>  productIds = requestDto.getProducts().stream()
                .map(OrderPreviewRequestDto.ProductItemDto::getProductId)
                .toList();

        // 주문 상품 정보 가져오기
        List<Product> products = getProducts(productIds);

        // 상품 가격 계산을 위한 Map 생성(key - id, value - Product)
        Map<Long, Product> productMap = new HashMap<>();
        products.forEach(product -> productMap.put(product.getId(), product));

        // 상품의 총 가격 계산
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderProduct> orderProducts = new ArrayList<>();

        for (OrderPreviewRequestDto.ProductItemDto itemDto : requestDto.getProducts()) {
            Product product = productMap.get(itemDto.getProductId());

            if (product == null) throw new EntityNotFoundException("상품 정보를 찾을 수 없습니다.");

            totalAmount = totalAmount.add(product.getPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity())));

            orderProducts.add(new OrderProduct(product.getId(), itemDto.getQuantity(), product.getPrice()));
        }

        // 임시 저장된 주문 업데이트
        savedOrder.updateOrderDetails(
                createOrderName(products),
                totalAmount,
                createActualOrderId(savedOrder.getId()),
                orderProducts);

        return OrderPreviewResponseDto.from(savedOrder);
    }

    @Transactional
    public PaymentResponseDto createPayment(PaymentRequestDto requestDto, User user) throws AccessDeniedException {
        // 요청이 들어온 주문의 존재 여부 확인
        Order order = orderRepository.findByOrderNumber(requestDto.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("해당 주문을 찾을 수 없습니다."));

        // 금액 및 상태 검증
        validateOrderForPayment(order, requestDto.getTotalAmount(), user.getId());

        // 결제 엔티티 생성
        Payment payment = Payment.builder()
                .order(order)
                .method(requestDto.getPaymentMethod())
                .amount(requestDto.getTotalAmount())
                .approvedAt(LocalDateTime.now())
                .paymentKey(createPaymentKey(requestDto.getPaymentMethod()))
                .build();

        // 주문 상태 변경
        order.markAsDone();

        return PaymentResponseDto.from(paymentRepository.save(payment));
    }

    // 주문명 생성 메서드
    private String createOrderName(List<Product> products) {
        String mainProductName = products.getFirst().getName();
        String orderName = products.size() > 1
                ? mainProductName + " 외 " + (products.size() - 1) + "건"
                : mainProductName;

        return orderName;
    }

    // 실제 주문 번호 생성 메서드
    private String createActualOrderId(Long id) {
        return "ORD-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-" + id;
    }

    // 결제키 생성 메서드
    private String createPaymentKey(String paymentMethod) {
        return paymentMethod + "-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-" + UUID.randomUUID().toString();
    }

    // 주문 검증 메서드
    private void validateOrderForPayment(Order order, BigDecimal requestAmount, Long userId) throws AccessDeniedException {
        // 주문에 저장된 금액과 요청 금액 검증
        if (order.getTotalAmount().compareTo(requestAmount) != 0) {
            throw new IllegalArgumentException("결제 금액이 일치하지 않습니다.");
        }

        // 결제가 완료된 주문인지 검증
        if (order.getStatus() != OrderStatus.READY) {
            throw new IllegalStateException("결제가 불가능한 주문입니다.");
        }

        // 주문의 주인과 현재 결제 요청자가 일치하는지 검증
        if (!order.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("본인의 주문만 결제할 수 있습니다.");
        }
    }

    // 나중에 진짜 Repo로 교체할 메서드
    private List<Product> getProducts(List<Long> ids) {
        return ids.stream()
                .map(id -> new Product(id, "임시 상품 " + id, new BigDecimal("10000.00")))
                .toList();
    }

    // 임시 Product 엔티티
    @Entity
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class Product {
        @Id
        private Long id;
        private String name;
        private BigDecimal price;
    }
}
