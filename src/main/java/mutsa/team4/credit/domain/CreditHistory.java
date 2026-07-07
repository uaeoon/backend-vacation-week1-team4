package mutsa.team4.credit.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class CreditHistory {

    @Id
    @GeneratedValue
    private Long creditHistoryId;

    @Column(nullable = false)
    private long amount;

    public enum CreditHistoryType {
        CHARGE,
        USE
    }
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CreditHistoryType type;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_id", nullable = false)
    private Credit credit;

    public static CreditHistory createCharge(Credit credit, long amount) {
        validate(credit, amount);

        return CreditHistory.builder()
                .amount(amount)
                .type(CreditHistoryType.CHARGE)
                .credit(credit)
                .build();
    }

    public static CreditHistory createUse(Credit credit, long amount) {
        validate(credit, amount);

        return CreditHistory.builder()
                .amount(amount)
                .type(CreditHistoryType.USE)
                .credit(credit)
                .build();
    }

    private static void validate(Credit credit, long amount) {
        if (credit == null) {
            throw new IllegalArgumentException("크레딧은 필수입니다.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("금액은 0보다 커야 합니다.");
        }
    }
}
