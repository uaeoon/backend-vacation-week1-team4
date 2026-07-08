package mutsa.team4.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import mutsa.team4.cart.domain.Cart;
import mutsa.team4.cart.domain.CartItem;

import java.util.List;

public class CartResponseDto {

    @Builder
    @Getter
    @AllArgsConstructor//build 메서드 호출용 생성자
    public static class CartInfoResponseDto {
        private List<CartItemInfoResponseDto> cartItems;
        private Long totalPrice;
        public static CartInfoResponseDto of(List<CartItemInfoResponseDto> cartItems, Long totalPrice){
            return CartInfoResponseDto.builder()
                    .cartItems(cartItems)
                    .totalPrice(totalPrice)
                    .build();
        }
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class CartItemInfoResponseDto {
        private Long cartItemId;
        private Long menuId;
        private String menuName;
        //옵션 이름 조회 후 넘겨주기
        private List<String> selectedOptionsNames;
        private Long expectPrice;
        private Long quantity;
        public static CartItemInfoResponseDto of(CartItem cartItem, String menuName, List<String> selectedOptionsNames, Long expectPrice){
            return CartItemInfoResponseDto.builder()
                    .cartItemId(cartItem.getCartItemId())
                    .menuId(cartItem.getMenuId())
                    .menuName(menuName)
                    .selectedOptionsNames(selectedOptionsNames)
                    .expectPrice(expectPrice)
                    .quantity(cartItem.getQuantity())
                    .build();
        }
    }

}
