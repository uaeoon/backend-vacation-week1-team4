package mutsa.team4.store.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class Category {

    @Id @GeneratedValue
    private Long categoryId;

    @Column(nullable = false, unique = true)
    private String categoryName;
    private static final Set<String> ALLOWED_CATEGORY_NAMES =
            Set.of("분식", "기타");

    public static Category create(
            String categoryName
    ) {
        if (categoryName == null || categoryName.isBlank()) {
            throw new IllegalArgumentException("카테고리명은 필수입니다.");
        }

        String name = categoryName.strip();
        if (!ALLOWED_CATEGORY_NAMES.contains(name)) {
            throw new IllegalArgumentException("카테고리는 분식 또는 기타만 가능합니다.");
        }

        return Category.builder()
                .categoryName(name)
                .build();
    }

}
