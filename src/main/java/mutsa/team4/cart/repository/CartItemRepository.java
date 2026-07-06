package mutsa.team4.cart.repository;

import mutsa.team4.cart.domain.Cart;
import mutsa.team4.cart.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
