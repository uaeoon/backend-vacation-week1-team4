package mutsa.team4.member.service;

import lombok.RequiredArgsConstructor;
import mutsa.team4.global.exception.GeneralException;
import mutsa.team4.global.security.JwtTokenProvider;
import mutsa.team4.member.code.MemberErrorCode;
import mutsa.team4.member.domain.Member;
import mutsa.team4.member.dto.MemberRequestDto;
import mutsa.team4.member.dto.MemberResponseDto;
import mutsa.team4.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public MemberResponseDto.MemberInfoResponseDto signup(MemberRequestDto.SignupRequestDto requestDto) {
        memberRepository.findByEmail(requestDto.getEmail())
                .ifPresent(m -> {
                    throw new GeneralException(MemberErrorCode.DUPLICATE_EMAIL);
                });
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        Member member = Member.createMember(
                requestDto.getName(),
                requestDto.getEmail(),
                encodedPassword);
        Member savedMember = memberRepository.save(member);

        return MemberResponseDto.MemberInfoResponseDto.of(
                savedMember.getMemberId(),
                savedMember.getName(),
                savedMember.getEmail()
        );
    }

    @Override
    public MemberResponseDto.MemberLoginResponseDto login(MemberRequestDto.LoginRequestDto requestDto) {
        //1. 회원 조회 검증
        Member member = memberRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new GeneralException(MemberErrorCode.MEMBER_NOT_FOUND));
        //2. 비밀번호 일치 검증
        if(!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())){
            throw new GeneralException(MemberErrorCode.INVALID_PASSWORD);
        }
        //3. 토큰 발금
        String accessToken = jwtTokenProvider.createAccessToken(member.getMemberId());
        return MemberResponseDto.MemberLoginResponseDto.of(accessToken);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberResponseDto.MemberInfoResponseDto getMemberInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(MemberErrorCode.MEMBER_NOT_FOUND));
        return MemberResponseDto.MemberInfoResponseDto.of(
                member.getMemberId(),
                member.getName(),
                member.getEmail()
        );
    }
}
