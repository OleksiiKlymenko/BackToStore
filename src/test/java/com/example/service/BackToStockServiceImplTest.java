package com.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.ProductCategory;
import com.example.exception.InvalidInputException;
import com.example.model.Product;
import com.example.model.User;
import com.example.priority.PriorityCalculator;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BackToStockServiceImplTest {
    private User john = new User("John", true, 16);
    private User bob = new User("Bob", false, 70);
    private User alice = new User("Alice", false, 20);
    private Product book = new Product("1", ProductCategory.BOOKS);
    private Product notValidProduct = new Product("2", ProductCategory.DIGITAL);
    private List<User> defaultSubscribedUsers = List.of(john, alice, bob);
    private BackToStockService backToStockService;

    @BeforeEach
    void setUp() {
        backToStockService = new BackToStockServiceImpl();
    }

    @Test
    void subscribe_mapWithOneUser_ok() throws NoSuchFieldException, IllegalAccessException {
        Field subscriptionsField = BackToStockServiceImpl.class.getDeclaredField("subscriptions");
        subscriptionsField.setAccessible(true);
        Map<Product, List<User>> subscriptions
                = (Map<Product, List<User>>) subscriptionsField.get(backToStockService);
        backToStockService.subscribe(john, book);
        Map<Product, List<User>> updatedSubscriptions
                = (Map<Product, List<User>>) subscriptionsField.get(backToStockService);
        List<User> subscribedUsers = updatedSubscriptions.getOrDefault(book, new ArrayList<>());
        assertTrue(subscribedUsers.contains(john), "User should be subscribed to the product.");
    }

    @Test
    void subscribe_nullUser_notOk() {
        InvalidInputException exception = assertThrows(InvalidInputException.class,
                () -> backToStockService.subscribe(bob, null));
        assertEquals("Product can not be null", exception.getMessage());
    }

    @Test
    void subscribedUsers_empty_ok() {
        List<User> subscribedUsers = backToStockService.subscribedUsers(book);
        assertTrue(subscribedUsers.isEmpty());
    }

    @Test
    void notifyUsers_validProduct_ok() throws NoSuchFieldException, IllegalAccessException {
        Field priorityCalculatorField = BackToStockServiceImpl.class
                .getDeclaredField("priorityCalculator");
        priorityCalculatorField.setAccessible(true);
        PriorityCalculator priorityCalculator = (PriorityCalculator) priorityCalculatorField
                .get(backToStockService);

        Field subscriptionsField = BackToStockServiceImpl.class.getDeclaredField("subscriptions");
        subscriptionsField.setAccessible(true);
        Map<Product, List<User>> subscriptions
                = (Map<Product, List<User>>) subscriptionsField.get(backToStockService);
        List<User> subscribedUsers = new ArrayList<>();
        subscribedUsers.add(bob);
        subscribedUsers.add(alice);
        subscriptions.put(book, subscribedUsers);
        List<User> notifiedUsers = backToStockService.notifyUsers(book);
        assertEquals(2, notifiedUsers.size());
        assertEquals(bob, notifiedUsers.get(0));
    }

    @Test
    void notifyUsers_emptyList_ok() {
        List<User> emptyListOfUsers = backToStockService.notifyUsers(notValidProduct);
        assertTrue(emptyListOfUsers.isEmpty());
    }
}
