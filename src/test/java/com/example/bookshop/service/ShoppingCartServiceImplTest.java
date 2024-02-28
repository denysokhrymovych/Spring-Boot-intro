package com.example.bookshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.example.bookshop.dto.cart.AddToCartRequestDto;
import com.example.bookshop.dto.cart.CartItemDto;
import com.example.bookshop.dto.cart.ShoppingCartDto;
import com.example.bookshop.mapper.ShoppingCartMapper;
import com.example.bookshop.model.Book;
import com.example.bookshop.model.CartItem;
import com.example.bookshop.model.ShoppingCart;
import com.example.bookshop.model.User;
import com.example.bookshop.repository.BookRepository;
import com.example.bookshop.repository.CartItemRepository;
import com.example.bookshop.repository.ShoppingCartRepository;
import com.example.bookshop.repository.UserRepository;
import com.example.bookshop.service.shoppingcart.ShoppingCartServiceImpl;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceImplTest {
    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private ShoppingCartMapper shoppingCartMapper;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;

    @Test
    @DisplayName("Add a book to the shopping cart")
    public void addToCart_ValidAddToCartRequestDto_ShouldReturnShoppingCartDto() {
        AddToCartRequestDto requestDto = getAddToCartRequestDto();
        Book book = getBook();
        User user = getUser();
        CartItem cartItem = getCartItem();
        CartItemDto cartItemDto = getCartItemDto();
        ShoppingCart shoppingCart = getShoppingCart();
        ShoppingCart shoppingCartWithItem = getShoppingCart()
                .setCartItems(new HashSet<>(Set.of(cartItem)));
        ShoppingCartDto expected = getShoppingCartDto()
                .setCartItems(new HashSet<>(Set.of(cartItemDto)));

        when(bookRepository.findById(requestDto.getBookId())).thenReturn(Optional.of(book));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(shoppingCartRepository.findByUserId(user.getId()))
                .thenReturn(Optional.of(shoppingCart));
        when(cartItemRepository.save(cartItem)).thenReturn(cartItem);
        when(shoppingCartMapper.toDto(shoppingCartWithItem)).thenReturn(expected);

        ShoppingCartDto actual = shoppingCartService.addToCart(requestDto, user.getId());
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Get a shopping cart by a valid user id")
    void getCartByUserId_ValidUserId_ShouldReturnShoppingCartDto() {
        ShoppingCart shoppingCart = getShoppingCart();
        User user = getUser();
        ShoppingCartDto expected = getShoppingCartDto();

        when(shoppingCartRepository.findByUserId(user.getId()))
                .thenReturn(Optional.of(shoppingCart));
        when(shoppingCartMapper.toDto(shoppingCart)).thenReturn(expected);

        ShoppingCartDto actual = shoppingCartService.getCartByUserId(user.getId());
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    private AddToCartRequestDto getAddToCartRequestDto() {
        return new AddToCartRequestDto()
                .setBookId(12L)
                .setQuantity(4);
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

    private CartItem getCartItem() {
        return new CartItem()
                .setShoppingCart(getShoppingCart())
                .setBook(getBook())
                .setQuantity(4);
    }

    private CartItemDto getCartItemDto() {
        return new CartItemDto()
                .setId(1L)
                .setBookId(12L)
                .setBookTitle("Effective Java")
                .setQuantity(4);
    }

    private ShoppingCart getShoppingCart() {
        return new ShoppingCart()
                .setId(1L)
                .setUser(getUser())
                .setCartItems(new HashSet<>());
    }

    private ShoppingCartDto getShoppingCartDto() {
        return new ShoppingCartDto()
                .setId(1L)
                .setUserId(19L)
                .setCartItems(new HashSet<>());
    }

    private User getUser() {
        return new User()
                .setId(19L)
                .setEmail("user999@gmail.com")
                .setPassword("$2a$10$EM4q69yQnQavd1Kv6uoBY.iyso.7l18i0eAdxad.vdaAVjFv/f4jW")
                .setFirstName("John")
                .setLastName("Doe")
                .setShippingAddress("Lviv")
                .setRoles(new HashSet<>());
    }
}
