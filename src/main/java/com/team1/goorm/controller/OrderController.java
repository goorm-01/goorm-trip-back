package com.team1.goorm.controller;

import com.team1.goorm.domain.dto.OrderPreviewRequestDto;
import com.team1.goorm.domain.dto.OrderPreviewResponseDto;
import com.team1.goorm.domain.dto.PaymentRequestDto;
import com.team1.goorm.domain.dto.PaymentResponseDto;
import com.team1.goorm.domain.entity.User;
import com.team1.goorm.repository.UserRepository;
import com.team1.goorm.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class OrderController {
    private final OrderService orderService;
    private final UserRepository userRepository;

    @PostMapping("/orders/preview")
    public ResponseEntity<OrderPreviewResponseDto> createOrderPreview(
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody  OrderPreviewRequestDto requestDto
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

        OrderPreviewResponseDto response = orderService.createOrder(requestDto, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/orders/payment")
    public ResponseEntity<PaymentResponseDto> createPayment(
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody PaymentRequestDto requestDto
    ) throws AccessDeniedException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

        PaymentResponseDto response = orderService.createPayment(requestDto, user);
        return ResponseEntity.ok(response);
    }
}
