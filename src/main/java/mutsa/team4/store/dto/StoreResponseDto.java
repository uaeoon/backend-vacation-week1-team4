package mutsa.team4.store.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import mutsa.team4.store.domain.Store;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StoreResponseDto {
    private Long storeId;
    private String storeName;
    private String storeImageUrl;
    private double rating;
    private Long categoryId;
    private String categoryName;

    public static StoreResponseDto from(Store store) {
        return new StoreResponseDto(
                store.getStoreId(),
                store.getStoreName(),
                store.getStoreImageUrl(),
                store.getRating(),
                store.getCategory().getCategoryId(),
                store.getCategory().getCategoryName()
        );
    }
}
