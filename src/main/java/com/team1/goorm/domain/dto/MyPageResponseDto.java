package com.team1.goorm.domain.dto;

import lombok.*;

import java.util.List;

@Data
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MyPageResponseDto {
    private UserInfoDto userInfo;
    private List<PaymentItemDto> paymentHistory;
    private List<CartItemDto> cartList;
}
