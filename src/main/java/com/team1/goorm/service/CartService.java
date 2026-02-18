package com.team1.goorm.service;

import com.team1.goorm.common.exception.BusinessException;
import com.team1.goorm.common.exception.ErrorCode;
import com.team1.goorm.domain.dto.CartRequestDto;
import com.team1.goorm.domain.dto.CartResponseDto;
import com.team1.goorm.domain.entity.Cart;
import com.team1.goorm.domain.entity.Product;
import com.team1.goorm.repository.CartRepository;
import com.team1.goorm.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    // 장바구니의 목록을 조회, userId는 1로 고정하고 전체 장바구니 목록을 조회
    @Transactional(readOnly = true)
    public List<CartResponseDto> getCartList() {
        return cartRepository.findAllByUserIdWithProduct(1L)
                .stream()
                .map(CartResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    // 장바구니의 목록을 추가, 요청을 받아 데이터를 정리하여 장바구니에 추가
    @Transactional
    public CartResponseDto addCart(CartRequestDto request) {
        System.out.println("productId: " + request.getProductId());
        if (request.getQuantity() < 1) {
            throw new BusinessException(ErrorCode.INVALID_REQUEST);
        }

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        Cart cart = Cart.builder()
                .userId(1L)
                .quantity(request.getQuantity())
                .departureDate(request.getDepartureDate())
                .product(product)
                .build();
        return CartResponseDto.fromEntity(cartRepository.save(cart));
    }

    // id 기반으로 데이터를 삭제
    @Transactional
    public void deleteCart(Long cartId) {
        if (!cartRepository.existsById(cartId)) {
            throw new BusinessException(ErrorCode.INVALID_REQUEST);
        }
        cartRepository.deleteById(cartId);
    }
}
