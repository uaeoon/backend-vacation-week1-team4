package mutsa.team4.cart.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mutsa.team4.global.apiPayload.code.status.GeneralErrorCode;
import mutsa.team4.global.exception.GeneralException;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
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

    public void updateQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getExpectPrice(){
        return 0L; //product 도메인 연동 후 구현
    }

    //수량 증가 버튼 대응
    public void increaseQuantity() {
        this.quantity+=1;
    }
    //수량 감소 버튼 대응
    public void decreaseQuantity() {
        if(this.quantity == 1) {
            //custom exception으로 추후 수정
            throw new GeneralException(GeneralErrorCode.BAD_REQUEST);

        }
        this.quantity-=1;
    }
}
