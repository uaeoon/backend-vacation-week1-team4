package mutsa.team4.credit.domain;

import jakarta.persistence.*;
import lombok.*;

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
            throw new IllegalArgumentException("충전할 수 없는 금액입니다.");
        }

        balance = Math.addExact(balance, amount);
    }

    public void use(long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("사용 금액은 0보다 커야 합니다.");
        }
        if (balance < amount) {
            throw new IllegalStateException("크레딧 잔액이 부족합니다.");
        }

        balance -= amount;
    }
}
