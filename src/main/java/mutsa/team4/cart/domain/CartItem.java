package mutsa.team4.cart.domain;

import jakarta.persistence.*;
import lombok.*;
import mutsa.team4.cart.code.CartErrorCode;
import mutsa.team4.global.exception.GeneralException;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
//service에서 사용하기 위한 builder 어노테이션 추가
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItem {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @Column(nullable = false)
    private Long menuId;
    //menu domain 개발 후 연관관계 매핑

    @ElementCollection
    @CollectionTable(
            name = "cart_item_option", //생성될 서브테이블 이름
            joinColumns = @JoinColumn(name = "cart_item_id") //외래키 컬럼 이름
    )
    //서브테이블에 들어갈 실제 밸류 네임
    @Column(name = "option_id")
    private List<Long> selectedOptions = new ArrayList<>();

    @Column(nullable = false)
    private Long quantity;

가    //수량 + 옵션 고려한 메뉴당 가격
    public Long getExpectPrice(){
        return 0L; //product 도메인 연동 후 구현
    }

    public void updateQuantity(Long quantity){
        if(quantity < 1){
            throw new GeneralException(CartErrorCode.INVALID_QUANTITY);
        }
        this.quantity = quantity;
    }
}
