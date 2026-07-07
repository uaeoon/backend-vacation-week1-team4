package mutsa.team4.store.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import mutsa.team4.store.domain.OptionGroup;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OptionGroupResponseDto {
    private Long optionGroupId;
    private String title;
    private boolean essential;
    private long maxSelectCount;
    private List<MenuOptionResponseDto> options;

    public static OptionGroupResponseDto from(
            OptionGroup optionGroup,
            List<MenuOptionResponseDto> options
    ) {
        return new OptionGroupResponseDto(
                optionGroup.getOptionGroupId(),
                optionGroup.getTitle(),
                optionGroup.isEssential(),
                optionGroup.getMaxSelectCount(),
                options
        );
    }
}
