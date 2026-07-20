package mutsa.team4.member.service;

import lombok.RequiredArgsConstructor;
import mutsa.team4.cart.domain.Cart;
import mutsa.team4.cart.repository.CartRepository;
import mutsa.team4.credit.domain.Credit;
import mutsa.team4.credit.repository.CreditRepository;
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
    private final CreditRepository creditRepository;
    private final CartRepository cartRepository;

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

        //현재 도메인끼리 매핑 없이 MemberId를 받아와서 생성하기 때문에 회원탈퇴 시에 장바구니와 크레딧이 남아있게 됨
        //추후 회원탈퇴 로직 구현시에 같이 지워줘야함.
        Credit credit = Credit.create(savedMember.getMemberId());
        creditRepository.save(credit);
        Cart cart = Cart.createCart(savedMember.getMemberId());
        cartRepository.save(cart);

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
        //3. 토큰 발급
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

    @Override
    public Member findOrCreateKakaoMember(String providerId, String email, String nickname) {
        // 1. 기존 카카오 회원 조회
        return memberRepository.findByProviderAndProviderId(Member.LoginProvider.KAKAO, providerId)
                .orElseGet(() -> {
                    // 2. 기존 회원 없을 시 카카오 회원 생성
                    Member member = Member.createKakaoMember(email, nickname, providerId);
                    Member savedMember = memberRepository.save(member);

                    // 3. 신규 회원 크레딧 생성 및 장바구니 생성
                    Credit credit = Credit.create(savedMember.getMemberId());
                    creditRepository.save(credit);

                    Cart cart = Cart.createCart(savedMember.getMemberId());
                    cartRepository.save(cart);

                    // 4. 저장된 회원 반환
                    return savedMember;
                });
    }
}
