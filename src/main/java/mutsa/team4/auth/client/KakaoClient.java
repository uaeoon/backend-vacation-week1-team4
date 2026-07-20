package mutsa.team4.auth.client;

import mutsa.team4.auth.dto.KakaoTokenResponseDto;
import mutsa.team4.auth.dto.KakaoUserResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class KakaoClient {
    // 카카오 access token 받기 위해 요청 보낼 URL
    private static final String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    // 카카오 사용자 정보를 조회할 URL
    private static final String KAKAO_USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    // 카카오 로그인 성공 후 백엔드 callback으로 온 code를 카카오에 대시 보내서 카카오 access token 요청
    public KakaoTokenResponseDto getAccessToken(String code) {
        // 1. 요청 Header 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 2. 요청 body 설정
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);

        // 3. Header, Body 하나의 요청 객체로 묶음
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        // 4. 카카오 토큰 API 호출
        ResponseEntity<KakaoTokenResponseDto> response =
                restTemplate.postForEntity(
                        KAKAO_TOKEN_URL,
                        request,
                        KakaoTokenResponseDto.class
                );

        // 5. 응답 Body에서 accessToken 담긴 DTO 반환
        return response.getBody();
    }

    // 카카오 access token으로 카카오 사용자 정보 요청
    public KakaoUserResponseDto getUserInfo(String kakaoAccessToken) {
        // 1. 요청 Header 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(kakaoAccessToken);

        // 2. Header 담은 요청 객체 생성
        HttpEntity<Void> request = new HttpEntity<>(headers);

        // 3. 카카오 사용자 정보 API 호출
        ResponseEntity<KakaoUserResponseDto> response =
                restTemplate.exchange(
                        KAKAO_USER_INFO_URL,
                        HttpMethod.GET,
                        request,
                        KakaoUserResponseDto.class
                );

        // 4. 응답 Body에서 사용자 정보 DTO 반환
        return response.getBody();
    }
}
