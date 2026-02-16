package com.team1.goorm.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_products")
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_product_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    // Product Entity가 없는 관계로 product id 조인 생략
    // 추후 Product Entity 작성 완료 시 수정
    private Long productId;

    // 사용자가 담은 상품 수
    @Column(name = "quantity")
    private int quantity;

    // 상품의 가격(총 가격 X)
    @Column(name = "price")
    private BigDecimal price;
}