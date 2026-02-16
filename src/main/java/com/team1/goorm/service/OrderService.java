package com.team1.goorm.service;

import com.team1.goorm.domain.dto.OrderPreviewRequestDto;
import com.team1.goorm.domain.dto.OrderPreviewResponseDto;
import com.team1.goorm.domain.entity.Order;
import com.team1.goorm.domain.entity.OrderProduct;
import com.team1.goorm.domain.entity.OrderStatus;
import com.team1.goorm.domain.entity.User;
import com.team1.goorm.repository.OrderRepository;
import com.team1.goorm.repository.PaymentRepository;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
                .build();
        Order savedOrder = orderRepository.save(order);

        // 주문의 상품들 ID를 리스트로 만들기
        List<Long>  productIds = requestDto.getProducts().stream()
                .map(OrderPreviewRequestDto.ProductItemDto::getProductId)
                .toList();

        // 주문 상품 정보 가져오기
        List<Product> products = getProducts(productIds);

        // 상품 가격 계산을 위한 Map 생성(key - id, value - Product)
        Map<Long, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, p -> p));

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

    private String createOrderName(List<Product> products) {
        String mainProductName = products.getFirst().getName();
        String orderName = products.size() > 1
                ? mainProductName + " 외 " + (products.size() - 1) + "건"
                : mainProductName;

        return orderName;
    }

    private String createActualOrderId(Long id) {
        return "ORD" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-" + id;
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
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;
        private BigDecimal price;
    }
}
