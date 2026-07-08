package mutsa.team4.order.service;

import lombok.RequiredArgsConstructor;
import mutsa.team4.cart.domain.Cart;
import mutsa.team4.cart.domain.CartItem;
import mutsa.team4.cart.repository.CartRepository;
import mutsa.team4.credit.domain.Credit;
import mutsa.team4.credit.domain.CreditHistory;
import mutsa.team4.credit.repository.CreditHistoryRepository;
import mutsa.team4.credit.repository.CreditRepository;
import mutsa.team4.global.exception.GeneralException;
import mutsa.team4.order.code.OrderErrorCode;
import mutsa.team4.order.domain.Order;
import mutsa.team4.order.domain.OrderItem;
import mutsa.team4.order.dto.OrderRequestDto;
import mutsa.team4.order.dto.OrderResponseDto;
import mutsa.team4.order.repository.OrderRepository;
import mutsa.team4.store.domain.Menu;
import mutsa.team4.store.domain.MenuOption;
import mutsa.team4.store.repository.MenuOptionRepository;
import mutsa.team4.store.repository.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CreditRepository creditRepository;
    private final MenuRepository menuRepository;
    private final MenuOptionRepository menuOptionRepository;
    private final CreditHistoryRepository creditHistoryRepository;

    @Override
    //1. 장바구니 조회
    public OrderResponseDto.OrderInfoResponseDto createOrder(Long memberId, OrderRequestDto.createOrderRequestDto orderRequestDto) {
        Cart cart = cartRepository.findByMemberId(memberId)
                .orElseThrow(() -> new GeneralException(OrderErrorCode.CART_NOT_FOUND));
        //2. 장바구니 빈 값 검증-> 비어있으면 에러
        List<CartItem> cartItems = cart.getCartItems();
        if(cartItems.isEmpty()){
            throw new GeneralException(OrderErrorCode.CART_EMPTY);
        }
        //3. 회원 크레딧 유무 조회
        Credit credit = creditRepository.findByMemberId(memberId)
                .orElseThrow(() -> new GeneralException(OrderErrorCode.CREDIT_NOT_FOUND));
        //4. 장바구니 레포로 주문상품 생성 및 총 금액 계산
        long totalOrderPrice = 0L;
        List<OrderItem> orderItems = new ArrayList<>();
        for(CartItem cartItem : cartItems){
            //4-1 실제 메뉴 상세 정보 조회
            Menu menu = menuRepository.findById(cartItem.getMenuId())
                    .orElseThrow(() -> new GeneralException(OrderErrorCode.MENU_NOT_FOUND));
            //4-2 옵션 엔티티 정보 조회
            List<MenuOption> menuOptions = menuOptionRepository.findAllById(cartItem.getSelectedOptions());
            //4-3 이름 추출 및 추가 금액 합산
            List<String> selectedOptionsNames = menuOptions.stream()
                    .map(MenuOption::getOptionName)
                    .collect(Collectors.toList());
            long optionsPrice = menuOptions.stream()
                    .mapToLong(MenuOption::getOptionPrice)
                    .sum();
            //4-4 상품별 옵션 고려 가격 계산
            long singleItemPrice = menu.getPrice() + optionsPrice;
            long itemRowTotalPrice = singleItemPrice * cartItem.getQuantity();
            //4-5 총가격 계산
            totalOrderPrice += itemRowTotalPrice;
            //4-6 OrderItem 조립
            OrderItem orderItem = OrderItem.createOrderItem(
                    cartItem.getMenuId(),
                    menu.getMenuName(),
                    cartItem.getQuantity(),
                    itemRowTotalPrice,
                    selectedOptionsNames
            );
            orderItems.add(orderItem);
        }
        //5. 회원 크레딧 차감
        credit.use(totalOrderPrice);
        //6. 결제 내역 저장
        CreditHistory creditHistory = CreditHistory.createUse(credit, totalOrderPrice);
        creditHistoryRepository.save(creditHistory);
        //7. 주문 최종 생성
        Order order = Order.createOrder(
                memberId,
                totalOrderPrice,
                orderItems
        );
        orderRepository.save(order);
        //8. 장바구니 비우기
        cart.getCartItems().clear();
        //9. 응답 DTO 반환 orderItemId의 식별키를 보여줘야 하는 경우를 대비해서 저장하고 다시 꺼내옴.
        List<OrderResponseDto.OrderItemInfoResponseDto> orderItemDtos = order.getOrderItems().stream()
                .map(OrderResponseDto.OrderItemInfoResponseDto::of)
                .collect(Collectors.toList());
        return OrderResponseDto.OrderInfoResponseDto.of(order, orderItemDtos);
    }



}