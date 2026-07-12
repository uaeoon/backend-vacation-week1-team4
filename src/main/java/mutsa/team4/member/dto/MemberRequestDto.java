package mutsa.team4.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

public class MemberRequestDto {

    @Getter
    public static class SignupRequestDto {
        @NotBlank(message = "이름은 필수 입력 값입니다.")
        private String name;

        @NotBlank(message = "아이디는 필수 입력 값입니다.")
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        private String email;
        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
                message = "비밀번호는 영문, 숫자, 특수문자를 포함하여 8자 이상이어야 합니다."
        )
        private String password;
    }

    @Getter
    public static class LoginRequestDto {
        @NotBlank(message = "아이디는 필수 입력 값입니다.")
        private String email;
        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        private String password;
    }
}
