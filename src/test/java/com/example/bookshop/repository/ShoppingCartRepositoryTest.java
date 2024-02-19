package com.example.bookshop.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.bookshop.exception.EntityNotFoundException;
import com.example.bookshop.model.ShoppingCart;
import com.example.bookshop.model.User;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ShoppingCartRepositoryTest {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Test
    @DisplayName("Find a shopping cart by a valid user id")
    @Sql(scripts = {
            "classpath:database/users/add-user-to-users-table.sql",
            "classpath:database/shopping-carts/add-shopping-cart-to-shopping-carts-table.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/users-roles/delete-relations-from-users-roles-table.sql",
            "classpath:database/shopping-carts/delete-shopping-cart-from-shopping-carts-table.sql",
            "classpath:database/users/delete-user-from-users-table.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findByUserId_ValidId_ShouldReturnShoppingCart() {
        User user = getUser();
        ShoppingCart expected = getShoppingCart();

        ShoppingCart actual = shoppingCartRepository.findByUserId(user.getId()).orElseThrow(
                () -> new EntityNotFoundException(
                        "Shopping cart not found by user id: " + user.getId()
                )
        );

        assertNotNull(actual);
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    private ShoppingCart getShoppingCart() {
        return new ShoppingCart()
                .setId(1L)
                .setUser(getUser())
                .setCartItems(Set.of());
    }

    private User getUser() {
        return new User()
                .setId(19L)
                .setEmail("user999@gmail.com")
                .setPassword("$2a$10$EM4q69yQnQavd1Kv6uoBY.iyso.7l18i0eAdxad.vdaAVjFv/f4jW")
                .setFirstName("John")
                .setLastName("Doe")
                .setShippingAddress("Lviv")
                .setRoles(Set.of());
    }
}
