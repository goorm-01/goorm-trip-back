package com.team1.goorm.domain.dto;

import com.team1.goorm.domain.entity.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PaymentResponseDto {
    private String orderId;
    private String paymentKey;
    private OrderStatus status;
    private LocalDateTime requestedAt;
    private LocalDateTime approvedAt;
}
