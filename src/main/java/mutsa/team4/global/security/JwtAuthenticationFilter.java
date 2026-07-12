package mutsa.team4.global.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtTokenProvider jwtTokenProvider;

    // 모든 HTTP 요청마다 실행되는 필터
    // Authorization Header에 담긴 JWT 검증 -> 인증된 사용자 정보 SecurityContext에 저장
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. 요청 Header에서 Authorization 값 꺼냄
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);

        // 2. Authorization Header 없거나 Bearer 형식 아니면 인증 처리 x -> 다음 필터로 넘김
        if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Bearer 접두사 제거 후 순서 JWT 문자열만 추출
        String token = authorizationHeader.substring(BEARER_PREFIX.length());

        // 4. 토큰 검증
        jwtTokenProvider.validateToken(token);

        // 5. 토큰에서 memberId 추출
        Long memberId = jwtTokenProvider.getMemberId(token);

        // 6. 인증된 사용자 정보 담는 AuthMember 객체 만듦
        AuthMember authMember = new AuthMember(memberId);

        // 7. Spring Security가 이해할 수 있는 Authentication 객체 만듦
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                    authMember,
                    null,
                    Collections.emptyList()
            );

        // 8. securityContext에 인증 정보 저장
        // Controller에서 @AuthenticationPrincipal AuthMember로 현재 로그인한 사용자 정보 꺼내기
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 9. 다음 필터로 요청 넘김
        filterChain.doFilter(request, response);
    }

}
