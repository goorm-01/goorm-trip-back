package com.team1.goorm.domain.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartRequestDto {
    private long productId; // 상품 Id
    private int quantity; // 수량
}
