package mutsa.team4.member.service;

import mutsa.team4.member.dto.MemberRequestDto;
import mutsa.team4.member.dto.MemberResponseDto;

public interface MemberService {
    MemberResponseDto.MemberInfoResponseDto signup(MemberRequestDto.SignupRequestDto requestDto);

    MemberResponseDto.MemberLoginResponseDto login(MemberRequestDto.LoginRequestDto requestDto);

    MemberResponseDto.MemberInfoResponseDto getMemberInfo(Long memberId);

}
