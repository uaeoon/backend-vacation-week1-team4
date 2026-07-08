package mutsa.team4.store.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mutsa.team4.global.apiPayload.code.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum StoreErrorCode implements BaseErrorCode {
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "STORE404_1", "카테고리를 찾을 수 없습니다."),
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "STORE404_2", "가게를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
