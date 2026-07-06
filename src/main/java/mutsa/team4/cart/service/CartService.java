package mutsa.team4.cart.service;

import mutsa.team4.cart.dto.CartRequestDto;
import mutsa.team4.cart.dto.CartResponseDto;

public interface CartService {

    //장바구니 조회
    CartResponseDto.CartInfoResponseDto getCart(Long memberId);

    //장바구니 담기(메뉴 추가)
    void addCartItem(Long memberId, CartRequestDto.AddCartItemRequestDto requestDto);

    //메뉴 수량 변경
    void updateCartItemQuantity(Long memberId, Long cartItemId, CartRequestDto.UpdateQuantity requestDto);

    //메뉴 삭제
    void deleteCartItem(Long memberId, Long cartItemId);
}
