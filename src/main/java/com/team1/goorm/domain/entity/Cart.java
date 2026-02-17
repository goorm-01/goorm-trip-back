package com.team1.goorm.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "cart") // 장바구니 테이블
public class Cart {
    @Id
    @Column(name = "cart_id") // 장바구니 id
    private int cartId;

    @Column(name = "product_id") // 상품 id
    private long productId;

    @Column(name = "quantity") // 상품 수량
    private int quantity;

    @Column(name = "user_id") // 유저 id
    private long userId;
}
