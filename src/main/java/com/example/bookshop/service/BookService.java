package com.example.bookshop.service;

import com.example.bookshop.dto.BookDto;
import com.example.bookshop.dto.CreateBookRequestDto;
import java.util.List;

public interface BookService {
    List<BookDto> getAll();

    BookDto getBookById(Long id);

    BookDto createBook(CreateBookRequestDto requestDto);

    BookDto updateBook(Long id, CreateBookRequestDto requestDto);

    void deleteBook(Long id);
}
