package mutsa.team4.store.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class Store {

    @Id @GeneratedValue
    private Long storeId;

    @Column(nullable = false)
    private String storeName;
    @Column(nullable = false)
    private String storeImageUrl;
    @Column(nullable = false)
    private double rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public static Store create(
            String storeName,
            String storeImageUrl,
            double rating,
            Category category
    ) {
        if (storeName == null || storeName.isBlank()) {
            throw new IllegalArgumentException("가게 이름은 필수입니다.");
        }
        if (storeImageUrl == null || storeImageUrl.isBlank()) {
            throw new IllegalArgumentException("가게 이미지 URL은 필수입니다.");
        }
        if (!Double.isFinite(rating)
            || rating < 0.0
            || rating > 5.0) {
            throw new IllegalArgumentException("별점은 0.0에서 5.0 사이여야 합니다.");
        }
        if (category == null) {
            throw new IllegalArgumentException("카테고리는 필수입니다.");
        }

        return Store.builder()
                .storeName(storeName)
                .storeImageUrl(storeImageUrl)
                .rating(rating)
                .category(category)
                .build();
    }

}
