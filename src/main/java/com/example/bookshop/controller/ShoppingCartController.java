package com.example.bookshop.controller;

import com.example.bookshop.dto.cart.AddToCartRequestDto;
import com.example.bookshop.dto.cart.ShoppingCartDto;
import com.example.bookshop.dto.cart.UpdateCartItemRequestDto;
import com.example.bookshop.model.User;
import com.example.bookshop.service.shoppingcart.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shopping cart management", description = "Endpoints for managing shopping carts")
@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Add a book to the shopping cart",
            description = "Add a book to the shopping cart")
    public ShoppingCartDto addToCart(
            @RequestBody @Valid AddToCartRequestDto requestDto,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.addToCart(requestDto, user.getId());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    @Operation(
            summary = "View the shopping cart",
            description = "View the shopping cart")
    public ShoppingCartDto getCart(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.getCartByUserId(user.getId());
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/cart-items/{cartItemId}")
    @Operation(
            summary = "Update the books quantity in the shopping cart",
            description = "Update the books quantity in the shopping cart")
    public ShoppingCartDto updateCartItemQuantity(
            @PathVariable Long cartItemId,
            @RequestBody @Valid UpdateCartItemRequestDto requestDto
    ) {
        return shoppingCartService.updateCartItemQuantity(cartItemId, requestDto);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/cart-items/{cartItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete a cart item from the shopping cart",
            description = "Delete a cart item from the shopping cart")
    public void deleteCartItem(@PathVariable Long cartItemId) {
        shoppingCartService.deleteCartItemById(cartItemId);
    }
}
