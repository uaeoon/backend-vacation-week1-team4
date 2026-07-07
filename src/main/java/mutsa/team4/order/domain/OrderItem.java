package mutsa.team4.order.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    private Long menuId;
    @Column(nullable = false)
    private Long quantity;
    @Column(nullable = false)
    private Long orderPrice;

    @ElementCollection
    @CollectionTable(name = "order_item_option",
            joinColumns = @JoinColumn(name = "order_item_id")
    )
    @Column(name = "option_id")
    @Builder.Default
    private List<Long> selectedOptions = new ArrayList<>();

    public void setOrder(Order order) {
        this.order = order;
    }

    public static OrderItem createOrderItem(Long menuId, Long quantity, Long orderPrice, List<Long> selectedOptions) {
        OrderItem orderItem = OrderItem.builder()
                .menuId(menuId)
                .quantity(quantity)
                .orderPrice(orderPrice)
                .selectedOptions(selectedOptions)
                .build();
        return orderItem;
    }
}
