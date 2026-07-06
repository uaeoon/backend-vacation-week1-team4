package mutsa.team4.cart.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mutsa.team4.global.apiPayload.code.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CartErrorCode implements BaseErrorCode {

    //400 Bad Request
    INVALID_QUANTITY(HttpStatus.BAD_REQUEST, "CART400_1", "장바구니 상품 수량은 1개 미만으로 설정할 수 없습니다."),
    //404 Not Found
    CART_NOT_FOUND(HttpStatus.NOT_FOUND, "CART404_1", "장바구니를 찾을 수 없습니다"),
    CART_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "CART404_2", "장바구니 상품을 찾을 수 없습니다");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}
