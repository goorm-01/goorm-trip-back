package com.team1.goorm.domain.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderProductDto {
    private String productName;
    private String startDate;
    private String endDate;
    private BigDecimal price;
    private int quantity;
}
