package com.example.bookshop.service.order;

import com.example.bookshop.dto.order.OrderDto;
import com.example.bookshop.dto.order.OrderItemDto;
import com.example.bookshop.dto.order.PlaceOrderRequestDto;
import com.example.bookshop.dto.order.UpdateOrderStatusRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderDto addOrder(Long userId, PlaceOrderRequestDto requestDto);

    List<OrderDto> getOrders(Long userId, Pageable pageable);

    OrderDto updateOrderStatus(Long orderId, UpdateOrderStatusRequestDto requestDto);

    OrderItemDto getOrderItem(Long itemId, Long orderId);

    List<OrderItemDto> getAllOrderItemsByOrderId(Long orderId, Pageable pageable);
}
