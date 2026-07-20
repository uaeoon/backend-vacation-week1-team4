package mutsa.team4.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoTokenResponseDto {

    // 카카오에서 발급한 access token
    @JsonProperty("access_token")
    String accessToken;
}
