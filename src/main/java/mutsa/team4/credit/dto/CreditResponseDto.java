package mutsa.team4.credit.dto;

import lombok.*;
import mutsa.team4.credit.domain.Credit;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreditResponseDto {
    private Long creditId;
    private long balance;

    public static CreditResponseDto from(Credit credit) {
        return new CreditResponseDto(
                credit.getCreditId(),
                credit.getBalance()
        );
    }
}
