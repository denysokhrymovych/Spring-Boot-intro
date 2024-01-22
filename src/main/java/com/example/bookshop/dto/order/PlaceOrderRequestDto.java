package com.example.bookshop.dto.order;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PlaceOrderRequestDto {
    @NotBlank(message = "Shipping address cannot be null or blank")
    private String shippingAddress;
}
