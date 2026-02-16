package com.team1.goorm.domain.dto;

import com.team1.goorm.domain.entity.Order;
import com.team1.goorm.domain.entity.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderPreviewResponseDto {
    private String orderName;
    private String orderId; // ORD-20260215-1 형태
    private OrderStatus status;
    private LocalDateTime createdAt;
    private UserInfoDto userInfo;

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class UserInfoDto {
        @NotNull
        private String name;
        @NotNull
        private String email;
    }

    public static OrderPreviewResponseDto from(Order order) {
        return OrderPreviewResponseDto.builder()
                .orderName(order.getOrderName())
                .orderId(order.getOrderNumber())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .userInfo(new UserInfoDto(order.getUser().getName(), order.getUser().getEmail()))
                .build();
    }
}
