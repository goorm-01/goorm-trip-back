package com.team1.goorm.domain.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CartItemDto {
    private Long productId;
    private String productName;
    private BigDecimal price;
    private int quantity;
    private String image;
}
