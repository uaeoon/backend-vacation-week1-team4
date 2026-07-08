package mutsa.team4.order.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mutsa.team4.global.apiPayload.code.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum OrderErrorCode implements BaseErrorCode {
    CART_EMPTY(HttpStatus.BAD_REQUEST, "ORDER400_1", "장바구니가 비어있어 주문할 수 없습니다."),
    INSUFFICIENT_BALANCE(HttpStatus.BAD_REQUEST, "ORDER400_2", "잔액이 부족합니다."),
    STORE_CLOSED(HttpStatus.BAD_REQUEST, "ORDER400_3", "현재 영업시간이 아니므로 주문할 수 없습니다."),

    CREDIT_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER404_1", "결제 계좌를 찾을 수 없습니다."),
    CART_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER404_2", "장바구니 정보를 찾을 수 없습니다"),
    MENU_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER404_3", "메뉴 정보를 찾을 수 없습니다");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
