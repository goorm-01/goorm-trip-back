package com.team1.goorm.controller;

import com.team1.goorm.common.response.ApiResponse;
import com.team1.goorm.domain.dto.CartRequestDto;
import com.team1.goorm.domain.dto.CartResponseDto;
import com.team1.goorm.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    // 장바구니 목록 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<CartResponseDto>>> getCartList() {
        return ResponseEntity.ok(
                ApiResponse.success("SUCCESS", "장바구니 조회에 성공했습니다.", cartService.getCartList())
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CartResponseDto>> addCart(@RequestBody CartRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.create("CART_ADD_SUCCESS", "장바구니에 상품이 추가되었습니다.", cartService.addCart(request))
        );
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<ApiResponse<Void>> deleteCart(@PathVariable Long cartId) {
        cartService.deleteCart(cartId);
        return ResponseEntity.ok(
                ApiResponse.success("CART_DELETE_SUCCESS", "장바구니 상품이 삭제되었습니다.", null)
        );
    }


}
