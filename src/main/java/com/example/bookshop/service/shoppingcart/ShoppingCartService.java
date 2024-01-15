package com.example.bookshop.service.shoppingcart;

import com.example.bookshop.dto.cart.AddToCartRequestDto;
import com.example.bookshop.dto.cart.ShoppingCartDto;
import com.example.bookshop.dto.cart.UpdateCartItemRequestDto;

public interface ShoppingCartService {
    ShoppingCartDto addToCart(AddToCartRequestDto requestDto, Long userId);

    ShoppingCartDto getCartByUserId(Long userId);

    ShoppingCartDto updateCartItemQuantity(Long cartItemId, UpdateCartItemRequestDto requestDto);

    void deleteCartItemById(Long cartId);
}
