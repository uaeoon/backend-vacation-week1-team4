package mutsa.team4.store.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class MenuOption {

    @Id @GeneratedValue
    private Long menuOptionId;

    @Column(nullable = false)
    private String optionName;
    @Column(nullable = false)
    private long optionPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_group_id", nullable = false)
    private OptionGroup optionGroup;

    public static MenuOption create(OptionGroup optionGroup, String optionName, long optionPrice) {
        if (optionGroup == null) {
            throw new IllegalArgumentException("옵션 그룹은 필수입니다.");
        }
        if (optionName == null || optionName.isBlank()) {
            throw new IllegalArgumentException("옵션 이름은 필수입니다.");
        }
        if (optionPrice < 0) {
            throw new IllegalArgumentException("옵션 가격은 0 이상이어야 합니다.");
        }

        return MenuOption.builder()
                .optionGroup(optionGroup)
                .optionName(optionName)
                .optionPrice(optionPrice)
                .build();
    }
}
