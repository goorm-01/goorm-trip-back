package com.team1.goorm.service;

import com.team1.goorm.domain.dto.CartItemDto;
import com.team1.goorm.domain.dto.MyPageResponseDto;
import com.team1.goorm.domain.dto.PaymentItemDto;
import com.team1.goorm.domain.entity.Payment;
import com.team1.goorm.domain.entity.User;
import com.team1.goorm.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPageService {
    private final PaymentRepository paymentRepository;

    // TODO: Cart 레포지토리 구현 후 추가

    public MyPageResponseDto buildMyPage(User user) {
        List<Payment> payments = getPayments(user);

        List<PaymentItemDto> paymentHistory = buildPaymentHistory(payments);

        // TODO: Cart 기능 구현 후 cart 조회 로직 추가 예정
        List<CartItemDto> cartList = new ArrayList<>();

        return MyPageResponseDto.builder()
                .paymentHistory(paymentHistory)
                .cartList(cartList)
                .build();
    }

    private List<Payment> getPayments(User user) {
        return paymentRepository.findTop5ByUserOrderByApprovedAtDesc(user);
    }

    private List<PaymentItemDto> buildPaymentHistory(List<Payment> payments) {
        return payments.stream()
                .map(payment -> PaymentItemDto.builder()
                            .orderNumber(payment.getOrder().getOrderNumber())
                            .orderName(payment.getOrder().getOrderName())
                            .totalAmount(payment.getOrder().getTotalAmount())
                            .orderStatus(payment.getOrder().getStatus())
                            .paymentDate(payment.getApprovedAt())
                            .build()
                ).toList();
    }
}
