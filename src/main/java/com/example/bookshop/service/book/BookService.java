package com.example.bookshop.service.book;

import com.example.bookshop.dto.book.BookDto;
import com.example.bookshop.dto.book.BookDtoWithoutCategoryIds;
import com.example.bookshop.dto.book.BookSearchParametersDto;
import com.example.bookshop.dto.book.CreateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    List<BookDto> getAll(Pageable pageable);

    BookDto getBookById(Long id);

    List<BookDtoWithoutCategoryIds> findAllByCategoryId(Long categoryId);

    BookDto createBook(CreateBookRequestDto requestDto);

    BookDto updateBook(Long id, CreateBookRequestDto requestDto);

    void deleteBook(Long id);

    List<BookDto> search(BookSearchParametersDto searchParameters);
}
