package mutsa.team4.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class CartResponseDto {

    @Builder
    @Getter
    @AllArgsConstructor//build 메서드 호출용 생성자
    public static class CartInfoResponseDto {
        private List<CartItemInfoResponseDto> cartItems;
        private Long totalPrice;
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
    }
}
