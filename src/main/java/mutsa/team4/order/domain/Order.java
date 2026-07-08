package mutsa.team4.order.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "orders")
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Order {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable = false)
    private Long memberId;
    //member 구현 후 one to one으로 매핑

    @Column(nullable = false)
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;

    @Column(nullable = false)
    private Long totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default//builder 사용시 NPE방지
    private List<OrderItem> orderItems = new ArrayList<>();

    public static Order createOrder(Long memberId, Long totalPrice, List<OrderItem> orderItems) {
        Order order = Order.builder()
                .memberId(memberId)
                .orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.ORDERED)
                .totalPrice(totalPrice)
                .build();

        for (OrderItem orderItem : orderItems) {
            order.orderItems.add(orderItem);
            //oder - oderItem 연결
            orderItem.setOrder(order);
        }
        return order;
    }


}
