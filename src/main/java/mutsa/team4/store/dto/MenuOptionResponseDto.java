package mutsa.team4.store.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import mutsa.team4.store.domain.MenuOption;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuOptionResponseDto {
    private Long menuOptionId;
    private String optionName;
    private long optionPrice;

    public static MenuOptionResponseDto from(MenuOption menuOption) {
        return new MenuOptionResponseDto(
                menuOption.getMenuOptionId(),
                menuOption.getOptionName(),
                menuOption.getOptionPrice()
        );
    }
}
