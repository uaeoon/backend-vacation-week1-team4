package mutsa.team4.auth.service;

import lombok.RequiredArgsConstructor;
import mutsa.team4.auth.client.KakaoClient;
import mutsa.team4.auth.dto.KakaoTokenResponseDto;
import mutsa.team4.auth.dto.KakaoUserResponseDto;
import mutsa.team4.global.security.JwtTokenProvider;
import mutsa.team4.member.domain.Member;
import mutsa.team4.member.service.MemberService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoAuthService {

    private final KakaoClient kakaoClient;
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${kakao.front-success-url}")
    private String frontSuccessUrl;

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    public String kakaoLogin(String code) {
        // 1. code로 카카오 토큰 받기
        KakaoTokenResponseDto tokenResponse = kakaoClient.getAccessToken(code);

        // 2. 카카오 accessToken 꺼내기
        String kakaoAccessToken = tokenResponse.getAccessToken();

        // 3. 카카오 사용자 정보 조회
        KakaoUserResponseDto kakaoUser = kakaoClient.getUserInfo(kakaoAccessToken);

        // 4. 우리 서비스 회원 조회 및 생성 (멤버 도메인 메서드 구현 필요)
        Member member = memberService.findOrCreateKakaoMember(
                kakaoUser.getProviderId(),
                kakaoUser.getEmail(),
                kakaoUser.getNickname()
        );

        // 5. 우리 서비스 JWT 발급
        String accessToken = jwtTokenProvider.createAccessToken(member.getMemberId());

        // 6. 프론트 성공 URL 생성
        return frontSuccessUrl + "?accessToken=" + accessToken + "&tokenType=Bearer";
    }

    public String getKakaoLoginUrl() {
        return "https://kauth.kakao.com/oauth/authorize"
                + "?client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&response_type=code";
    }
}
