package com.example.bookshop.dto.book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Data;
import org.hibernate.validator.constraints.ISBN;

@Data
public class CreateBookRequestDto {
    @NotNull(message = "Title cannot be null")
    @Size(max = 255, message = "Title cannot be greater than 255 characters")
    private String title;

    @NotNull(message = "Author cannot be null")
    @Size(max = 255, message = "Author cannot be greater than 255 characters")
    private String author;

    @NotNull(message = "ISBN cannot be null")
    @ISBN(message = "Invalid format of ISBN")
    private String isbn;

    @NotNull(message = "Price cannot be null")
    @Min(0)
    private BigDecimal price;

    private String description;
    private String coverImage;
}
