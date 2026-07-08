package mutsa.team4.store.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import mutsa.team4.store.domain.Menu;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuResponseDto {
    private Long menuId;
    private String menuName;
    private String description;
    private long price;
    private List<OptionGroupResponseDto> optionGroups;

    public static MenuResponseDto from(
            Menu menu,
            List<OptionGroupResponseDto> optionGroups
    ) {
        return new MenuResponseDto(
                menu.getMenuId(),
                menu.getMenuName(),
                menu.getDescription(),
                menu.getPrice(),
                optionGroups
        );
    }
}
