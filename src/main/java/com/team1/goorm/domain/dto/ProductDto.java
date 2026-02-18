package com.team1.goorm.domain.dto;

import com.team1.goorm.domain.entity.Product;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private String productName;
    private String category;
    private int price;
    private String image;

    // 엔티티를 DTO로 변환
    public static ProductDto fromEntity(Product product) {
        return ProductDto.builder()
                .productName(product.getProductName())
                .category(product.getCategory())
                .price(product.getPrice())
                .image(product.getImage())
                .build();
    }
}