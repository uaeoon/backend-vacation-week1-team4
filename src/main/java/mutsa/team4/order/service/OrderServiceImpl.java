package mutsa.team4.order.service;

import lombok.RequiredArgsConstructor;
import mutsa.team4.order.dto.OrderRequestDto;
import mutsa.team4.order.dto.OrderResponseDto;
import mutsa.team4.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;

    @Override
    public OrderResponseDto.OrderInfoResponseDto createOrder(Long memberId, OrderRequestDto.createOrderRequestDto orderRequestDto) {
        return null;
    }
}
