package mutsa.team4.credit.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor
public class CreditChargeRequestDto {
    @NotNull(message = "충전 금액은 필수입니다.")
    private Long amount;
}
