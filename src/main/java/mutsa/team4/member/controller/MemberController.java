package mutsa.team4.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.team4.global.apiPayload.ApiResponse;
import mutsa.team4.global.security.AuthMember;
import mutsa.team4.member.dto.MemberRequestDto;
import mutsa.team4.member.dto.MemberResponseDto;
import mutsa.team4.member.service.MemberService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    //1. 회원가입
    @PostMapping("/auth/signup")
    public ApiResponse<MemberResponseDto.MemberInfoResponseDto> signup(@RequestBody@Valid MemberRequestDto.SignupRequestDto requestDto) {
        MemberResponseDto.MemberInfoResponseDto response = memberService.signup(requestDto);
        return ApiResponse.onSuccess("회원가입에 성공했습니다.", response);
    }
    //2. 로그인

    @PostMapping("/auth/login")
    public ApiResponse<MemberResponseDto.MemberLoginResponseDto> login(@RequestBody@Valid MemberRequestDto.LoginRequestDto requestDto) {
        MemberResponseDto.MemberLoginResponseDto response = memberService.login(requestDto);
        return ApiResponse.onSuccess("로그인에 성공했습니다.", response);
    }
    //3. 내 정보 조회(JWT 토큰 기반)
    @PostMapping("/members/me")
    public ApiResponse<MemberResponseDto.MemberInfoResponseDto> getMemberInfo(@AuthenticationPrincipal AuthMember authMember) {
        Long memberId = authMember.getMemberId();
        MemberResponseDto.MemberInfoResponseDto response = memberService.getMemberInfo(memberId);
        return ApiResponse.onSuccess("내 정보 조회에 성공했습니다.", response);
    }
}
