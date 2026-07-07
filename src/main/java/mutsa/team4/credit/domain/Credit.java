package mutsa.team4.credit.domain;

import jakarta.persistence.*;
import lombok.*;
import mutsa.team4.credit.code.CreditErrorCode;
import mutsa.team4.global.exception.GeneralException;

import java.util.Set;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, unique = true)
    private Member member;

    public static Credit create(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("회원은 필수입니다.");
        }

        return Credit.builder()
                .balance(0L)
                .member(member)
                .build();
    }

    private static final Set<Long> CHARGE_AMOUNTS =
            Set.of(1_000L, 3_000L, 5_000L, 10_000L);

    public void charge(long amount) {
        if (!CHARGE_AMOUNTS.contains(amount)) {
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
