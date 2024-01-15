package com.example.bookshop.dto.cart;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateCartItemRequestDto {
    @Positive(message = "Quantity must be greater than 0")
    private int quantity;
}
