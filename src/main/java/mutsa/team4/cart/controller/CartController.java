package mutsa.team4.cart.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.team4.cart.dto.CartRequestDto;
import mutsa.team4.cart.dto.CartResponseDto;
import mutsa.team4.cart.service.CartService;
import mutsa.team4.global.apiPayload.ApiResponse;
import mutsa.team4.global.security.AuthMember;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    //장바구니 조회 API
    @GetMapping
    public ApiResponse<CartResponseDto.CartInfoResponseDto> getCart(
            @AuthenticationPrincipal AuthMember authMember
    ) {
        Long memberId = authMember.getMemberId();
        CartResponseDto.CartInfoResponseDto response = cartService.getCart(memberId);
        return ApiResponse.onSuccess("장바구니 조회 성공", response);
    }

    //장바구니 담기 (메뉴 추가) API
    @PostMapping("/items")
    public ApiResponse<Void> addCartItem(
            @AuthenticationPrincipal AuthMember authMember,
            @RequestBody@Valid CartRequestDto.AddCartItemRequestDto requestDto
    ) {
        Long memberId = authMember.getMemberId();
        cartService.addCartItem(memberId, requestDto);
        return ApiResponse.onSuccess("장바구니에 상품을 성공적으로 담았습니다");
    }
    //장바구니 수량 변경
    @PatchMapping("/items/{cartItemId}")
    public ApiResponse<Void> updateCartItemQuantity(
            @AuthenticationPrincipal AuthMember authMember,
            @PathVariable("cartItemId") Long cartItemId,
            @RequestBody@Valid CartRequestDto.UpdateQuantity requestDto
    ) {
        Long memberId = authMember.getMemberId();
        cartService.updateCartItemQuantity(memberId, cartItemId, requestDto);
        return ApiResponse.onSuccess("상품 수량이 변경되었습니다.");

    }

    @DeleteMapping("/items/{cartItemId}")
    public ApiResponse<Void> deleteCartItem(
            @AuthenticationPrincipal AuthMember authMember,
            @PathVariable("cartItemId") Long cartItemId
    ) {
        Long memberId = authMember.getMemberId();
        cartService.deleteCartItem(memberId, cartItemId);
        return ApiResponse.onSuccess("장바구니에서 상품을 삭제했습니다.");
    }




}
