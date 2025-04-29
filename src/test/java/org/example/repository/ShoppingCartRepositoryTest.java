package org.example.repository;

import org.example.config.CustomMySqlContainer;
import org.example.model.ShoppingCart;
import org.example.model.User;
import org.example.repository.shoppingCart.ShoppingCartRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ShoppingCartRepositoryTest {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private EntityManager entityManager;

    private CustomMySqlContainer mySqlContainer;
    private User user;

    @BeforeAll
    void setUpContainer() {
        mySqlContainer = CustomMySqlContainer.getInstance();
        mySqlContainer.start();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setFirstName("John");
        user.setLastName("Doe");

        entityManager.persist(user);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);

        shoppingCart = shoppingCartRepository.save(shoppingCart);
    }

    @Test
    void findByUser_WhenShoppingCartExists_ShouldReturnShoppingCart() {
        Optional<ShoppingCart> result = shoppingCartRepository.findByUser(user);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(user.getId(), result.get().getUser().getId());
    }

    @Test
    void findByUser_WhenShoppingCartDoesNotExist_ShouldReturnEmpty() {
        User newUser = new User();
        newUser.setEmail("newuser@example.com");
        newUser.setPassword("newpassword");
        newUser.setFirstName("Jane");
        newUser.setLastName("Smith");

        entityManager.persist(newUser);

        Optional<ShoppingCart> result = shoppingCartRepository.findByUser(newUser);

        Assertions.assertTrue(result.isEmpty());
    }

    @AfterAll
    void tearDownContainer() {
        mySqlContainer.stop();
    }
}
