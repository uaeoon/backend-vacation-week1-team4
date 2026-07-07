package mutsa.team4.cart.service;

import lombok.RequiredArgsConstructor;
import mutsa.team4.cart.code.CartErrorCode;
import mutsa.team4.cart.domain.Cart;
import mutsa.team4.cart.domain.CartItem;
import mutsa.team4.cart.dto.CartRequestDto;
import mutsa.team4.cart.dto.CartResponseDto;
import mutsa.team4.cart.repository.CartRepository;
import mutsa.team4.global.exception.GeneralException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor//의존성 주입을 위한 생성자처리
@Transactional
public class CartServiceImpl implements CartService{
    private final CartRepository cartRepository;

    //장바구니 조회
    @Override
    //추후 멤버 도메인 생기면 읽기전용으로 바꾸기(현재 service 레이어에서 카트 없으면 생성해주는 로직 있어서 충돌남
//    @Transactional(readOnly = true)
    public CartResponseDto.CartInfoResponseDto getCart(Long memberId) {
        Cart cart = cartRepository.findByMemberId(memberId)
                .orElseGet(() -> cartRepository.save(Cart.createCart(memberId)));
                //장바구니 없을 경우 생성해서 리턴 -> 나중에 회원 가입시 자동 생성 관계로 변경필요?

        //CartItem 꺼내서 Dto로 포장
        List<CartResponseDto.CartItemInfoResponseDto> cartItems = cart.getCartItems().stream()
                .map(item -> CartResponseDto.CartItemInfoResponseDto.of(item,"임시 메뉴 이름", Collections.emptyList()))
                .collect(Collectors.toList());
        //최종 response 리턴
        return CartResponseDto.CartInfoResponseDto.of(cart, cartItems);
    }

    @Override
    public void addCartItem(Long memberId, CartRequestDto.AddCartItemRequestDto requestDto) {
        Cart cart = cartRepository.findByMemberId(memberId)
                .orElseGet(() -> cartRepository.save(Cart.createCart(memberId)));

        //원본값 수정 방지를 위해 새로운 리스트로 복사
        List<Long> selectedOptions = new ArrayList<>(requestDto.getSelectedOptions());
        //옵션까지 확인한 중복메뉴 추가 처리를 위해 옵션 리스트 정렬
        Collections.sort(selectedOptions);

        //장바구니 동일 조합 메뉴 확인
        CartItem existingItem = cart.getCartItems().stream()
                .filter(item -> item.getMenuId().equals(requestDto.getMenuId()))
                .filter(item -> {
                    List<Long> itemOptions = new ArrayList<>(item.getSelectedOptions());
                    Collections.sort(itemOptions);
                    return itemOptions.equals(selectedOptions);
                })
                .findFirst()
                .orElse(null);

        //동일조합 메뉴 있으면 기존 수량 업데이트
        if(existingItem != null) {
            existingItem.updateQuantity(existingItem.getQuantity() + requestDto.getQuantity());
        } else {
            CartItem newCartItem = CartItem.createCartItem(cart, requestDto.getMenuId(), selectedOptions, requestDto.getQuantity());
            cart.addCartItem(newCartItem);
        }

    }

    @Override
    public void updateCartItemQuantity(Long memberId, Long cartItemId, CartRequestDto.UpdateQuantity requestDto) {
        Cart cart = cartRepository.findByMemberId(memberId)
                .orElseThrow(() -> new GeneralException(CartErrorCode.CART_NOT_FOUND));

        //[검증] 내 장바구니 안에 해당 상품이 존재하는지 찾기(중복삭제 방지와 타인 장바구니 조작 방지)
        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getCartItemId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new GeneralException(CartErrorCode.CART_ITEM_NOT_FOUND));

        cartItem.updateQuantity(requestDto.getQuantity());
    }

    @Override
    public void deleteCartItem(Long memberId, Long cartItemId) {
        Cart cart = cartRepository.findByMemberId(memberId)
                .orElseThrow(() -> new GeneralException(CartErrorCode.CART_NOT_FOUND));

        //[검증] 내 장바구니 안에 해당 상품이 존재하는지 찾기(중복삭제 방지와 타인 장바구니 조작 방지)
        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getCartItemId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new GeneralException(CartErrorCode.CART_ITEM_NOT_FOUND));

        cart.removeCartItem(cartItem);

    }
}
