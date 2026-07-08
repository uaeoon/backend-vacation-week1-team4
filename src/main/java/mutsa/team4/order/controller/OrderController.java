package mutsa.team4.order.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.team4.global.apiPayload.ApiResponse;
import mutsa.team4.order.dto.OrderRequestDto;
import mutsa.team4.order.dto.OrderResponseDto;
import mutsa.team4.order.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ApiResponse<OrderResponseDto.OrderInfoResponseDto> createOrder(@RequestBody@Valid OrderRequestDto.createOrderRequestDto orderRequestDto) {
        //추후 멤버 기능 구현시 수정
        Long memberId = 1L;
        OrderResponseDto.OrderInfoResponseDto response = orderService.createOrder(memberId, orderRequestDto);
        return ApiResponse.onSuccess("주문이 성공적으로 완료되었습니다.", response);
    }
}
