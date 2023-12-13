package com.example.bookshop.repository;

import com.example.bookshop.model.Book;
import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<Book> getAll();

    Optional<Book> findBookById(Long id);

    Book createBook(Book book);
}
