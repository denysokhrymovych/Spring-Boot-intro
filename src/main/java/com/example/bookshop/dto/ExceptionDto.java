package com.example.bookshop.dto;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ExceptionDto {
    private LocalDateTime timestamp;
    private HttpStatus status;
    private String message;
}
