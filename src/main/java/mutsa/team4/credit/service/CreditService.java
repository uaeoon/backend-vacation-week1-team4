package mutsa.team4.credit.service;

import lombok.RequiredArgsConstructor;
import mutsa.team4.credit.domain.Credit;
import mutsa.team4.credit.domain.CreditHistory;
import mutsa.team4.credit.dto.CreditChargeRequestDto;
import mutsa.team4.credit.dto.CreditResponseDto;
import mutsa.team4.credit.repository.CreditHistoryRepository;
import mutsa.team4.credit.repository.CreditRepository;
import mutsa.team4.global.apiPayload.code.status.GeneralErrorCode;
import mutsa.team4.global.exception.GeneralException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CreditService {

    private final CreditRepository creditRepository;
    private final CreditHistoryRepository creditHistoryRepository;

    // 잔액 조회
    public CreditResponseDto getBalance(Long memberId) {
        Credit credit = findCreditByMemberId(memberId);

        return CreditResponseDto.from(credit);
    }

    // 잔액 충전
    @Transactional
    public CreditResponseDto creditCharge(Long memberId, CreditChargeRequestDto requestDto) {
        Credit credit = findCreditByMemberId(memberId);

        long amount = requestDto.getAmount();
        credit.charge(amount);

        CreditHistory history = CreditHistory.createCharge(credit, amount);
        creditHistoryRepository.save(history);

        return CreditResponseDto.from(credit);
    }

    private Credit findCreditByMemberId(Long memberId) {
        return creditRepository.findByMember_MemberId(memberId)
                .orElseThrow(() -> new GeneralException(GeneralErrorCode.NOT_FOUND)
                );
    }
}
