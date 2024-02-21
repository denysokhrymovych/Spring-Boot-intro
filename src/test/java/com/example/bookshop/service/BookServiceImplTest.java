package com.example.bookshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.example.bookshop.dto.book.BookDto;
import com.example.bookshop.dto.book.CreateBookRequestDto;
import com.example.bookshop.exception.EntityNotFoundException;
import com.example.bookshop.mapper.BookMapper;
import com.example.bookshop.model.Book;
import com.example.bookshop.model.Category;
import com.example.bookshop.repository.BookRepository;
import com.example.bookshop.repository.CategoryRepository;
import com.example.bookshop.service.book.BookServiceImpl;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    @Mock
    private BookMapper bookMapper;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    @DisplayName("Create a new book")
    void createBook_ValidCreateBookRequestDto_ShouldReturnBookDto() {
        Book book = getBook();
        BookDto expected = getBookDto();
        CreateBookRequestDto requestDto = getCreateBookRequestDto();
        when(bookMapper.toModel(requestDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(expected);
        when(categoryRepository.findAllById(requestDto.getCategoryIds()))
                .thenReturn(List.of(getCategory()));

        BookDto actual = bookService.createBook(requestDto);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Get a book with a valid id")
    void getBookById_ValidId_ShouldReturnValidBookDto() {
        Book book = getBook();
        BookDto expected = getBookDto();
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(expected);

        BookDto actual = bookService.getBookById(book.getId());
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Get a book by an invalid id")
    void getBookById_InvalidId_ShouldThrowException() {
        Long bookId = 100L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(
                EntityNotFoundException.class,
                () -> bookService.getBookById(bookId)
        );

        String expected = "Cannot get book by id: " + bookId;
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Get all books")
    void getAll_OneBook_ShouldReturnList() {
        Book book = getBook();
        List<Book> books = List.of(book);
        Pageable pageable = PageRequest.of(0, 5);
        Page<Book> page = new PageImpl<>(books, pageable, books.size());
        BookDto responseDto = getBookDto();

        when(bookRepository.findAll(pageable)).thenReturn(page);
        when(bookMapper.toDto(book)).thenReturn(responseDto);

        List<BookDto> expected = List.of(responseDto);
        List<BookDto> actual = bookService.getAll(pageable);

        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Update a book with a valid id")
    void updateBook_ValidId_ShouldReturnBookDto() {
        Book bookBeforeUpdating = getBook();
        CreateBookRequestDto requestDto = getCreateBookRequestDto();

        requestDto.setPrice(new BigDecimal("37.00"));
        Book updatedBook = getBook().setPrice(new BigDecimal("37.00"));

        BookDto expected = getBookDto().setPrice(new BigDecimal("37.00"));
        when(bookRepository.findById(bookBeforeUpdating.getId()))
                .thenReturn(Optional.of(bookBeforeUpdating));
        when(bookMapper.toModel(requestDto)).thenReturn(updatedBook);
        when(bookRepository.save(updatedBook)).thenReturn(updatedBook);
        when(bookMapper.toDto(updatedBook)).thenReturn(expected);

        BookDto actual = bookService.updateBook(bookBeforeUpdating.getId(), requestDto);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Update a book by an invalid id")
    void updateBook_InvalidId_ShouldThrowException() {
        Long bookId = 50L;
        CreateBookRequestDto requestDto = getCreateBookRequestDto();
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(
                EntityNotFoundException.class,
                () -> bookService.updateBook(bookId, requestDto)
        );

        String expected = "Book not found with id: " + bookId;
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    private Book getBook() {
        return new Book()
                .setId(12L)
                .setTitle("Effective Java")
                .setAuthor("Joshua Bloch")
                .setDescription("Comprehensive guidebook offering essential best practices")
                .setPrice(new BigDecimal("30.00"))
                .setIsbn("9780134686097");
    }

    private BookDto getBookDto() {
        return new BookDto()
                .setId(12L)
                .setTitle("Effective Java")
                .setAuthor("Joshua Bloch")
                .setDescription("Comprehensive guidebook offering essential best practices")
                .setPrice(new BigDecimal("30.00"))
                .setIsbn("9780134686097");
    }

    private CreateBookRequestDto getCreateBookRequestDto() {
        return new CreateBookRequestDto()
                .setTitle("Effective Java")
                .setAuthor("Joshua Bloch")
                .setIsbn("9780134686097")
                .setPrice(new BigDecimal("30.00"))
                .setDescription("Comprehensive guidebook offering essential best practices")
                .setCategoryIds(Set.of(12L));
    }

    private Category getCategory() {
        return new Category()
                .setId(4L)
                .setName("Programming")
                .setDescription("Books about programming");
    }
}
