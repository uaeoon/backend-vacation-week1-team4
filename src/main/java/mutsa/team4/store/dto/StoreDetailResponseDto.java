package mutsa.team4.store.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import mutsa.team4.store.domain.Store;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StoreDetailResponseDto {
    private Long storeId;
    private String storeName;
    private List<MenuResponseDto> menus;

    public static StoreDetailResponseDto from(
            Store store,
            List<MenuResponseDto> menus
    ) {
        return new StoreDetailResponseDto(
                store.getStoreId(),
                store.getStoreName(),
                menus
        );
    }
}
