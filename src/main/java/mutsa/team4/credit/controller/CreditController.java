package mutsa.team4.credit.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.team4.credit.dto.CreditChargeRequestDto;
import mutsa.team4.credit.dto.CreditResponseDto;
import mutsa.team4.credit.service.CreditService;
import mutsa.team4.global.apiPayload.ApiResponse;
import mutsa.team4.global.security.AuthMember;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/credits")
@RequiredArgsConstructor
public class CreditController {
    private final CreditService creditService;

    // 잔액 조회
    @GetMapping
    public ResponseEntity<ApiResponse<CreditResponseDto>> getBalance(
            @AuthenticationPrincipal AuthMember authMember
    ) {

        Long memberId = authMember.getMemberId();
        CreditResponseDto response = creditService.getBalance(memberId);

        return ResponseEntity.ok(
                ApiResponse.onSuccess("크레딧 잔액 조회에 성공했습니다.", response)
        );
    }

    // 잔액 충전
    @PostMapping("/charge")
    public ResponseEntity<ApiResponse<CreditResponseDto>> creditCharge(
            @AuthenticationPrincipal AuthMember authMember,
            @Valid @RequestBody CreditChargeRequestDto requestDto
    ) {

        Long memberId = authMember.getMemberId();
        CreditResponseDto response = creditService.creditCharge(memberId, requestDto);

        return ResponseEntity.ok(
                ApiResponse.onSuccess("크레딧 충전에 성공했습니다.", response)
        );
    }
}
