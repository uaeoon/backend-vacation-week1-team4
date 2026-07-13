package mutsa.team4.global.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {

    // application.yaml에 정의한 secret key 주입 받음
    @Value("${jwt.secret}")
    private String secret;
    // Access Token 만료 시간, application.yaml에서 ms 단위로 관리
    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiration;

    // 문자열 그대로 사용하지 않고 실행 시 SecretKey로 변환
    private SecretKey secretKey;

    // 환경 변수로 받은 secret 문자열 SecretKey 객체로 변환
    @PostConstruct
    protected void init() {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // Access Token 생성
    // 멤버 도메인 생성 후 memberId 받아서 토큰 생성하는 방식으로 수정 예정
    public String createAccessToken(Long memberId) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + accessTokenExpiration);

        return Jwts.builder()
                .subject(String.valueOf(memberId))
                .issuedAt(now)
                .expiration(expiration)
                .signWith(secretKey)
                .compact();
    }

    // 토큰에서 memberId 꺼냄
    public Long getMemberId(String token) {
        Claims claims = parseClaims(token);
        return Long.valueOf(claims.getSubject());
    }

    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // JWT 파싱하고 Claims(payload) 꺼내는 내부 메서드
    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
