package mutsa.team4.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Properties;

@Getter
@NoArgsConstructor
public class KakaoUserResponseDto {

    // providerId로 사용
    private Long id;
    // 카카오 프로필 정보
    private Properties properties;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    // id 문자열로 변환
    public String getProviderId() {
        return String.valueOf(id);
    }

    public String getEmail() {
        return kakaoAccount.email;
    }

    public String getNickname() {
        return properties.nickname;
    }

    @Getter
    @NoArgsConstructor
    public static class Properties {
        private String nickname;
    }

    @Getter
    @NoArgsConstructor
    public static class KakaoAccount {
        private String email;
    }
}
