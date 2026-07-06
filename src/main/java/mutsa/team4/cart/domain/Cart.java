package mutsa.team4.cart.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long memberId;
    //회원가입 및 로그인 구현 후 Member 도메인과 매칭

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();

    //cart 생성 메소드
    public static Cart createCart(Long memberId) {
        Cart cart = new Cart();
        cart.memberId = memberId;
        return cart;
    }

    //장바구니 음식 금액 총합
    public Long getTotalPrice() {
        return this.cartItems.stream()
                .mapToLong(CartItem::getExpectPrice)
                .sum();
    }

    //장바구니 아이템 삭제
    public void removeCartItem(CartItem cartItem) {
        this.cartItems.remove(cartItem);
    }
    //장바구니 아이템 추가
    public void addCartItem(CartItem cartItem) {
        this.cartItems.add(cartItem);
    }


}
