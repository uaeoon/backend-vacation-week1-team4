package mutsa.team4.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class MemberResponseDto {
    //회원정보 조회 응답 Dto
    @AllArgsConstructor
    @Getter
    @Builder
    public static class MemberInfoResponseDto {
        private Long memberId;
        private String name;
        private String email;

        public static MemberInfoResponseDto of(Long memberId, String name, String email){
            return MemberInfoResponseDto.builder()
                    .memberId(memberId)
                    .name(name)
                    .email(email)
                    .build();
        }
    }

    //로그인 응답 Dto
    @Getter
    @Builder
    @AllArgsConstructor
    public static class MemberLoginResponseDto {
        private String accessToken;

        public static MemberLoginResponseDto of(String accessToken){
            return MemberLoginResponseDto.builder()
                    .accessToken(accessToken)
                    .build();
        }
    }
}
