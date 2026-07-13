package mutsa.team4.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    // 어떤 요청 허용할지, 어떤 요청에 인증을 요구할지, 어떤 필터를 사용할지
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 서버가 세션 사용하지 않으므로 CSRF 보호 비활성화
                .csrf(csrf -> csrf.disable())

                // CorsConfigurationSource Bean에 정의한 Cors 정책 적용
                .cors(cors -> {})

                // 매 요청마다 Authorization Header의 토큰으로 사용자 인증
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 요청별 접근 권한
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/signup").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/stores/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // 위에서 허용한 요청 외에는 인증 필요
                        .anyRequest().authenticated()
                )
                // 인증되지 않은 사용자가 인증 필요한 API에 접근 시 실행
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )
                // JWT 인증 필터 등록
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        // H2 Console을 위한 설정
        http.headers(headers ->
                headers.frameOptions(frameOptions -> frameOptions.disable())
        );

        return http.build();
    }

    // 비밀번호 암호화, 로그인 시 입력 비밀번호와 암호화된 비밀번호 비교할 때 사용
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 프론트 로컬 개발 서버 주소 허용
        configuration.setAllowedOrigins(List.of(
                "http://localhost:5173"
        ));

        // 프론트에서 쓸 HTTP Method 허용
        configuration.setAllowedMethods(List.of(
                "GET",
                "POST",
                "PATCH",
                "PUT",
                "DELETE",
                "OPTIONS"
        ));

        // Authorization Header 허용
        configuration.setAllowedHeaders(List.of(
            "Authorization",
            "Content-Type"
        ));

        // 프론트에서 Authorization Header 읽을 수 있도록 노출
        configuration.setExposedHeaders(List.of(
                "Authorization"
        ));

        // 모든 API 경로에 Cors 정책 적용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
