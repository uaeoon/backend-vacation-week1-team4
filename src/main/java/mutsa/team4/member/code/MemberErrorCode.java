package mutsa.team4.member.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mutsa.team4.global.apiPayload.code.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER404_1", "존재하지 않는 회원입니다."),
    DUPLICATE_LOGIN_ID(HttpStatus.BAD_REQUEST, "MEMBER400_1", "이미 사용 중인 아이디입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "MEMBER400_2", "비밀번호가 일치하지 않습니다."),
    DUPLICATE_NAME(HttpStatus.BAD_REQUEST, "MEMBER400_3", "이미 사용 중인 이름입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
