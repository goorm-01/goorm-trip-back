package com.team1.goorm.domain.dto;

import com.team1.goorm.domain.entity.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PaymentItemDto {
    private String orderNumber;
    private String orderName;
    private BigDecimal totalAmount;
    private OrderStatus orderStatus;
    private LocalDateTime paymentDate;
}
