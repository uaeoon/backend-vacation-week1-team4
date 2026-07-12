package mutsa.team4.global.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import mutsa.team4.global.apiPayload.ApiResponse;
import mutsa.team4.global.apiPayload.code.status.GeneralErrorCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    // 인증되지 않은 사용자가 보호된 API에 접근할 때 실행
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        // 401 Unauthorized
        objectMapper.writeValue(
                response.getWriter(),
                ApiResponse.onFailure(
                        GeneralErrorCode.UNAUTHORIZED,
                        authException.getMessage()
                )
        );
    }
}
