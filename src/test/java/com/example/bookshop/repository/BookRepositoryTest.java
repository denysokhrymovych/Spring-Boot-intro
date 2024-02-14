package com.example.bookshop.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.bookshop.model.Book;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {
    private static List<Book> expectedList = new ArrayList<>();

    @Autowired
    private BookRepository bookRepository;

    @BeforeAll
    static void beforeAll() {
        Book firstBook = new Book()
                .setId(4L)
                .setTitle("Effective Java")
                .setAuthor("Joshua Bloch")
                .setIsbn("9780134686097")
                .setPrice(new BigDecimal("30.00"));
        Book secondBook = new Book()
                .setId(6L)
                .setTitle("Programming Pearls")
                .setAuthor("Jon Bentley")
                .setIsbn("9780134498034")
                .setPrice(new BigDecimal("36.00"));

        expectedList.add(firstBook);
        expectedList.add(secondBook);
    }

    @Test
    @DisplayName("Find all books by an existing category id")
    @Sql(scripts = {
            "classpath:database/books/add-books-to-books-table.sql",
            "classpath:database/categories/add-categories-to-categories-table.sql",
            "classpath:database/books-categories/add-relations-to-books-categories-table.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/books-categories/delete-relations-from-books-categories-table.sql",
            "classpath:database/books/delete-books-from-books-table.sql",
            "classpath:database/categories/delete-categories-from-categories-table.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findAllByCategoriesId_WithProgrammingCategory_ReturnsTwoBooks() {
        Long categoryId = 4L;

        List<Book> actual = bookRepository.findAllByCategoriesId(categoryId);

        int expectedSize = 2;
        assertEquals(expectedSize, actual.size());
        assertEquals(expectedList, actual);
    }

    @Test
    @DisplayName("Find all books by a non-existing category id")
    @Sql(scripts = {
            "classpath:database/books/add-books-to-books-table.sql",
            "classpath:database/categories/add-categories-to-categories-table.sql",
            "classpath:database/books-categories/add-relations-to-books-categories-table.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/books-categories/delete-relations-from-books-categories-table.sql",
            "classpath:database/books/delete-books-from-books-table.sql",
            "classpath:database/categories/delete-categories-from-categories-table.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findAllByCategoriesId_NonExistingCategoryId_ReturnsEmptyList() {
        Long categoryId = 15L;

        List<Book> actual = bookRepository.findAllByCategoriesId(categoryId);

        List<Book> emptyList = new ArrayList<>();
        assertEquals(emptyList, actual);
    }
}
