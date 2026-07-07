package mutsa.team4.store.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class OptionGroup {

    @Id @GeneratedValue
    private Long optionGroupId;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private boolean essential;
    @Column(nullable = false)
    private long maxSelectCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    public static OptionGroup create(Menu menu, String title, Boolean essential, Long maxSelectCount) {
        if (menu == null) {
            throw new IllegalArgumentException("메뉴가 존재하지 않습니다.");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("옵션 그룹명은 필수입니다.");
        }
        if (maxSelectCount <= 0) {
            throw new IllegalArgumentException("선택 상한선은 1 이상이어야 합니다.");
        }

        return OptionGroup.builder()
                .menu(menu)
                .title(title)
                .essential(essential)
                .maxSelectCount(maxSelectCount)
                .build();
    }
}
