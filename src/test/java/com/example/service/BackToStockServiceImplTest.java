package com.example.service;

import com.example.ProductCategory;
import com.example.exception.InvalidInputException;
import com.example.model.Product;
import com.example.model.User;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BackToStockServiceImplTest {
    User John = new User("John", true, 16);
    User Bob = new User("Bob", false, 70);
    User Alice = new User("Alice", false, 20);
    Product book = new Product("1", ProductCategory.BOOKS);
    Product notValidProduct = new Product("2", ProductCategory.DIGITAL);
    private List<User> defaultSubscribedUsers = List.of(John, Alice, Bob);
    private BackToStockService backToStockService;

    @BeforeEach
    void setUp() {
        backToStockService = new BackToStockServiceImpl();
    }

    @Test
    void subscribeAndSubscribedUsers_ok() {
        backToStockService.subscribe(John, book);
        backToStockService.subscribe(Alice, book);
        backToStockService.subscribe(Bob, book);
        List<User> subscribedUsers = backToStockService.subscribedUsers(book);
        assertEquals(subscribedUsers, defaultSubscribedUsers);
    }

    @Test
    void subscribe_nullUser_notOk() {
        InvalidInputException exception = assertThrows(InvalidInputException.class,
                () -> backToStockService.subscribe(Bob, null));
        assertEquals("Product can not be null", exception.getMessage());
    }

    @Test
    void subscribedUsers_ok() {
        List<User> subscribedUsers = backToStockService.subscribedUsers(book);
        assertTrue(subscribedUsers.isEmpty());
    }

    @Test
    void notifyUsers_ok() {
        backToStockService.subscribe(Alice, book);
        backToStockService.subscribe(Bob, book);
        backToStockService.subscribe(John, book);
        List<User> orderQueueOfUsers = backToStockService.notifyUsers(book);
        assertEquals(orderQueueOfUsers, List.of(John, Bob, Alice));
    }

    @Test
    void notifyUsers_emptyList_ok() {
        List<User> emptyListOfUsers = backToStockService.notifyUsers(notValidProduct);
        assertTrue(emptyListOfUsers.isEmpty());
    }
}