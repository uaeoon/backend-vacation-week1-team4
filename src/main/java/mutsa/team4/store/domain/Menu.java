package mutsa.team4.store.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class Menu {

    @Id @GeneratedValue
    private Long menuId;

    @Column(nullable = false)
    private String menuName;
    @Column(nullable = false)
    private long price;
    private String description;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    public static Menu create(Store store, String menuName, long price, String description) {
        if (store == null) {
            throw new IllegalArgumentException("가게가 존재하지 않습니다.");
        }
        if (menuName == null || menuName.isBlank()) {
            throw new IllegalArgumentException("메뉴 이름은 필수입니다.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("메뉴 가격은 0원 이상이어야 합니다.");
        }

        return Menu.builder()
                .store(store)
                .menuName(menuName)
                .price(price)
                .description(description)
                .build();
    }
}
