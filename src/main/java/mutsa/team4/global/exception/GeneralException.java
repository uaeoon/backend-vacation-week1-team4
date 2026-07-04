package mutsa.team4.global.exception;

import lombok.Getter;
import mutsa.team4.global.apiPayload.code.BaseErrorCode;

@Getter
public class GeneralException extends RuntimeException{
    private final BaseErrorCode errorCode;
    public GeneralException(BaseErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
