package com.team1.goorm.domain.dto;

import com.team1.goorm.domain.entity.OrderStatus;
import com.team1.goorm.domain.entity.Payment;
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

    public static PaymentResponseDto from(Payment payment) {
        return PaymentResponseDto.builder()
                .orderId(payment.getOrder().getOrderNumber())
                .paymentKey(payment.getPaymentKey())
                .status(payment.getOrder().getStatus())
                .requestedAt(payment.getOrder().getCreatedAt())
                .approvedAt(payment.getApprovedAt())
                .build();
    }
}
