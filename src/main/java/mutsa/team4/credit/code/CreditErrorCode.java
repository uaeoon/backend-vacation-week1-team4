package mutsa.team4.credit.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mutsa.team4.global.apiPayload.code.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CreditErrorCode implements BaseErrorCode {
    CREDIT_NOT_FOUND(HttpStatus.NOT_FOUND, "CREDIT404_1", "크레딧 정보를 찾을 수 없습니다."),
    INVALID_CHARGE_AMOUNT(HttpStatus.BAD_REQUEST, "CREDIT400_1", "충전은 1000원 단위여야 합니다."),
    INSUFFICIENT_BALANCE(HttpStatus.BAD_REQUEST, "CREDIT400_2", "크레딧 잔액이 부족합니다."),
    INVALID_USE_AMOUNT(HttpStatus.BAD_REQUEST, "CREDIT400_3", "사용 금액은 0보다 커야 합니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
