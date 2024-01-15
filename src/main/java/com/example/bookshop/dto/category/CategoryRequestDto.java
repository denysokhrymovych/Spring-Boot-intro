package com.example.bookshop.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryRequestDto {
    @NotBlank(message = "Name cannot be null or blank")
    @Size(max = 30, message = "Name cannot be greater than 30 characters")
    private String name;
    @NotBlank(message = "Description cannot be null or blank")
    @Size(max = 500, message = "Description cannot be greater than 500 characters")
    private String description;
}
