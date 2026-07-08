package mutsa.team4.order.service;

import mutsa.team4.order.dto.OrderRequestDto;
import mutsa.team4.order.dto.OrderResponseDto;

public interface OrderService {

    OrderResponseDto.OrderInfoResponseDto createOrder(Long memberId, OrderRequestDto.createOrderRequestDto orderRequestDto);
}
