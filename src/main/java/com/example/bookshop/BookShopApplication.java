package com.example.bookshop;

import com.example.bookshop.model.Book;
import com.example.bookshop.service.BookService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookShopApplication {
    private final BookService bookService;

    @Autowired
    public BookShopApplication(BookService bookService) {
        this.bookService = bookService;
    }

    public static void main(String[] args) {
        SpringApplication.run(BookShopApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Book book = new Book();
            book.setTitle("Title");
            book.setAuthor("Author");
            book.setIsbn("978-3-16-148410-0");
            book.setPrice(BigDecimal.valueOf(100));
            book.setDescription("Description");
            book.setCoverImage("Cover image");

            bookService.save(book);

            System.out.println(bookService.findAll());
        };
    }

}
