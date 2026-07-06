package mutsa.team4.cart.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import jakarta.validation.constraints.NotNull;

public class CartRequestDto {
    @Getter
    @Setter
    public static class AddCartItemRequestDto {
        @NotNull(message = "메뉴 ID는 필수 입력값입니다.")
        private Long menuId;

        @NotNull(message = "수량은 필수 입력값입니다.")
        @Min(value = 1, message = "수량은 최소 1개 이상이어야 합니다.")
        private Long quantity;

        @NotNull(message = "옵션 정보는 필수 입력 값입니다. (없을 시 빈 리스트)")
        private List<Long> selectedOptions;
    }

    @Getter
    @Setter
    public static class UpdateQuantity {
        @NotNull(message = "수량은 필수 입력값입니다.")
        @Min(value = 1, message = "수량은 최소 1개 이상이어야 합니다.")
        private Long quantity;
    }
}
