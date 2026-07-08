package mutsa.team4.order.dto;

import lombok.*;
import mutsa.team4.order.domain.Order;
import mutsa.team4.order.domain.OrderItem;
import mutsa.team4.order.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponseDto {

    @Builder(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class OrderInfoResponseDto{
        private Long orderId;
        private Long totalPrice;
        private OrderStatus orderStatus;
        private LocalDateTime orderDate;
        private List<OrderItemInfoResponseDto> orderItems;

        public static OrderInfoResponseDto of(Order order, List<OrderItemInfoResponseDto> orderItems){
            return OrderInfoResponseDto.builder()
                    .orderId(order.getOrderId())
                    .totalPrice(order.getTotalPrice())
                    .orderStatus(order.getOrderStatus())
                    .orderDate(order.getOrderDate())
                    .orderItems(orderItems)
                    .build();
        }
    }

    @Builder(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class OrderItemInfoResponseDto {
        private Long menuId;
        private String menuName;
        private Long quantity;
        private Long orderPrice;
        private List<String> selectedOptionsNames;

        public static OrderItemInfoResponseDto of(OrderItem orderItem){
            return OrderItemInfoResponseDto.builder()
                    .menuId(orderItem.getMenuId())
                    .menuName(orderItem.getMenuName())
                    .quantity(orderItem.getQuantity())
                    .orderPrice(orderItem.getOrderPrice())
                    .selectedOptionsNames(orderItem.getSelectedOptions())
                    .build();
        }
    }
}
