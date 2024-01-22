package com.example.bookshop.controller;

import com.example.bookshop.dto.order.OrderDto;
import com.example.bookshop.dto.order.OrderItemDto;
import com.example.bookshop.dto.order.PlaceOrderRequestDto;
import com.example.bookshop.dto.order.UpdateOrderStatusRequestDto;
import com.example.bookshop.model.User;
import com.example.bookshop.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order management", description = "Endpoints for managing orders")
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Place a new order",
            description = "Place a new order")
    public OrderDto placeOrder(
            @RequestBody @Valid PlaceOrderRequestDto requestDto,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderService.addOrder(user.getId(), requestDto);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    @Operation(
            summary = "Get order history",
            description = "Retrieve user's order history")
    public List<OrderDto> getOrders(
            Authentication authentication,
            Pageable pageable
    ) {
        User user = (User) authentication.getPrincipal();
        return orderService.getOrders(user.getId(), pageable);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    @Operation(
            summary = "Update order status",
            description = "Update the status of an order")
    public OrderDto updateOrderStatus(
            @RequestBody @Valid UpdateOrderStatusRequestDto requestDto,
            @PathVariable(name = "id") Long orderId) {
        return orderService.updateOrderStatus(orderId, requestDto);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{order-id}/items")
    @Operation(
            summary = "Get order items",
            description = "Retrieve all order items for a specific order")
    public List<OrderItemDto> getOrderItemsDto(
            @PathVariable(name = "order-id") Long orderId,
            Pageable pageable
    ) {
        return orderService.getAllOrderItemsByOrderId(orderId, pageable);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{order-id}/items/{item-id}")
    @Operation(
            summary = "Get an order item",
            description = "Retrieve a specific order item within an order")
    public OrderItemDto getOrderItem(
            @PathVariable(name = "order-id") Long orderId,
            @PathVariable(name = "item-id") Long itemId
    ) {
        return orderService.getOrderItem(orderId, itemId);
    }
}
