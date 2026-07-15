package mutsa.team4.credit.domain;

import jakarta.persistence.*;
import lombok.*;
import mutsa.team4.credit.code.CreditErrorCode;
import mutsa.team4.global.exception.GeneralException;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class Credit {

    @Id @GeneratedValue
    private Long creditId;

    @Column(nullable = false)
    private long balance;

    // 회원가입 및 로그인 로직 구현 후 Member Domain과 매칭 예정
    @Column(nullable = false, unique = true)
    private Long memberId;

    public static Credit create(Long memberId) { // memberId 대신 member로 수정 예정
        if (memberId == null) {
            throw new IllegalArgumentException("회원은 필수입니다.");
        }

        return Credit.builder()
                .balance(0L)
                .memberId(memberId)
                .build();
    }

    private static final long CHARGE_UNIT = 1_000L;

    public void charge(long amount) {
        if (amount <= 0 || amount % CHARGE_UNIT != 0) {
            throw new GeneralException(
                    CreditErrorCode.INVALID_CHARGE_AMOUNT
            );
        }

        balance = Math.addExact(balance, amount);
    }

    public void use(long amount) {
        if (amount <= 0) {
            throw new GeneralException(
                    CreditErrorCode.INVALID_USE_AMOUNT
            );
        }
        if (balance < amount) {
            throw new GeneralException(
                    CreditErrorCode.INSUFFICIENT_BALANCE
            );
        }

        balance -= amount;
    }
}
