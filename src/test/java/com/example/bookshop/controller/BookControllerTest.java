package com.example.bookshop.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.bookshop.dto.book.BookDto;
import com.example.bookshop.dto.book.CreateBookRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {
    protected static MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(
            @Autowired WebApplicationContext applicationContext
    ) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Create a new book")
    @Sql(scripts = {
            "classpath:database/books-categories/delete-relations-from-books-categories-table.sql",
            "classpath:database/books/delete-created-book.sql",
            "classpath:database/categories/delete-categories-from-categories-table.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void createBook_ValidRequestDto_Success() throws Exception {
        CreateBookRequestDto requestDto = getValidCreateBookRequestDto();

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(
                        post("/books")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                BookDto.class
        );

        BookDto expected = getBookDto();
        assertNotNull(actual);
        assertNotNull(actual.getId());
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Create a new book with a null title")
    void createBook_InvalidRequestDto_BadRequest() throws Exception {
        CreateBookRequestDto requestDto = getInvalidCreateBookRequestDto();

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(
                        post("/books")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    private CreateBookRequestDto getValidCreateBookRequestDto() {
        return new CreateBookRequestDto()
                .setTitle("Effective Java")
                .setAuthor("Joshua Bloch")
                .setIsbn("9780134686097")
                .setPrice(new BigDecimal("30.00"))
                .setDescription("Comprehensive guidebook offering essential best practices")
                .setCategoryIds(Set.of(12L));
    }

    private CreateBookRequestDto getInvalidCreateBookRequestDto() {
        return new CreateBookRequestDto()
                .setAuthor("Joshua Bloch")
                .setIsbn("9780134686097")
                .setPrice(new BigDecimal("30.00"))
                .setDescription("Comprehensive guidebook offering essential best practices")
                .setCategoryIds(Set.of(12L));
    }

    private BookDto getBookDto() {
        return new BookDto()
                .setId(12L)
                .setTitle("Effective Java")
                .setAuthor("Joshua Bloch")
                .setIsbn("9780134686097")
                .setPrice(new BigDecimal("30.00"))
                .setDescription("Comprehensive guidebook offering essential best practices")
                .setCategoryIds(Set.of(12L));
    }
}
