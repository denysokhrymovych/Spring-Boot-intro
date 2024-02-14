package com.example.bookshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.example.bookshop.dto.category.CategoryRequestDto;
import com.example.bookshop.dto.category.CategoryResponseDto;
import com.example.bookshop.exception.EntityNotFoundException;
import com.example.bookshop.mapper.CategoryMapper;
import com.example.bookshop.model.Category;
import com.example.bookshop.repository.CategoryRepository;
import com.example.bookshop.service.category.CategoryServiceImpl;
import java.util.List;
import java.util.Optional;
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
class CategoryServiceImplTest {
    @Mock
    private CategoryMapper categoryMapper;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("Save a category")
    void save_ValidCategoryRequestDto_ShouldReturnCategoryResponseDto() {
        Category category = getCategory();
        CategoryResponseDto expected = getCategoryResponseDto();
        CategoryRequestDto requestDto = getCategoryRequestDto();
        when(categoryMapper.toModel(requestDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(expected);

        CategoryResponseDto actual = categoryService.save(requestDto);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Get a category with a valid id")
    void getById_ValidId_ShouldReturnCategoryResponseDto() {
        Category category = getCategory();
        CategoryResponseDto expected = getCategoryResponseDto();
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(expected);

        CategoryResponseDto actual = categoryService.getById(category.getId());
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Get a category by an invalid id")
    void getById_InvalidId_ShouldThrowException() {
        Long categoryId = 60L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(
                EntityNotFoundException.class,
                () -> categoryService.getById(categoryId)
        );

        String expected = "Category not found with id: " + categoryId;
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Get all categories")
    void getAll_OneCategory_ShouldReturnList() {
        Category category = getCategory();
        List<Category> categories = List.of(category);
        Pageable pageable = PageRequest.of(0, 5);
        Page<Category> page = new PageImpl<>(categories, pageable, categories.size());
        CategoryResponseDto responseDto = getCategoryResponseDto();

        when(categoryRepository.findAll(pageable)).thenReturn(page);
        when(categoryMapper.toDto(category)).thenReturn(responseDto);

        List<CategoryResponseDto> expected = List.of(responseDto);
        List<CategoryResponseDto> actual = categoryService.getAll(pageable);

        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Update a category with a valid id")
    void update_ValidId_ShouldReturnCategoryResponseDto() {
        Category categoryBeforeUpdating = getCategory();
        CategoryRequestDto requestDto = getCategoryRequestDto();

        requestDto.setDescription("Science books");
        Category updatedCategory = getCategory().setDescription("Science books");

        CategoryResponseDto expected = getCategoryResponseDto().setDescription("Science books");
        when(categoryRepository.findById(categoryBeforeUpdating.getId()))
                .thenReturn(Optional.of(categoryBeforeUpdating));
        when(categoryMapper.toModel(requestDto)).thenReturn(updatedCategory);
        when(categoryRepository.save(updatedCategory)).thenReturn(updatedCategory);
        when(categoryMapper.toDto(updatedCategory)).thenReturn(expected);

        CategoryResponseDto actual = categoryService.update(
                categoryBeforeUpdating.getId(), requestDto);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Update a category by an invalid id")
    void update_InvalidId_ShouldThrowException() {
        Long categoryId = 60L;
        CategoryRequestDto requestDto = getCategoryRequestDto();
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(
                EntityNotFoundException.class,
                () -> categoryService.update(categoryId, requestDto)
        );

        String expected = "Category not found with id: " + categoryId;
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    private Category getCategory() {
        return new Category()
                .setId(5L)
                .setName("Science")
                .setDescription("Books about science");
    }

    private CategoryRequestDto getCategoryRequestDto() {
        return new CategoryRequestDto()
                .setName("Science")
                .setDescription("Books about science");
    }

    private CategoryResponseDto getCategoryResponseDto() {
        return new CategoryResponseDto()
                .setId(5L)
                .setName("Science")
                .setDescription("Books about science");
    }
}
