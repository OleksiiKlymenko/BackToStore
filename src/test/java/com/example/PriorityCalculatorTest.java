package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.exception.InvalidInputException;
import com.example.model.Product;
import com.example.model.User;
import com.example.priority.PriorityCalculator;
import com.example.priority.PriorityCalculatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PriorityCalculatorTest {
    private static final String JOHN_NAME = "John";
    private static final String BOB_NAME = "Bob";
    private static final boolean PREMIUM = true;
    private static final boolean NOT_PREMIUM = false;
    private static final int JOHN_AGE = 33;
    private static final int BOB_AGE = 70;
    private static final String HIGH_PRIORITY = "HIGH";
    private static final String MEDIUM_PRIORITY = "MEDIUM";
    private User premiumUser;
    private User oldUser;
    private Product bookProduct;
    private Product medicalProduct;
    private PriorityCalculator priorityCalculator;

    @BeforeEach
    void setUp() {
        premiumUser = new User(JOHN_NAME, PREMIUM, JOHN_AGE);
        oldUser = new User(BOB_NAME, NOT_PREMIUM, BOB_AGE);
        bookProduct = new Product("1", ProductCategory.BOOKS);
        medicalProduct = new Product("2", ProductCategory.MEDICAL);
        priorityCalculator = new PriorityCalculatorImpl();
    }

    @Test
    void calculatePriority_forPremiumUser_ok() {
        Priority priority = priorityCalculator.calculatePriority(premiumUser, bookProduct);
        String userPriority = priority.name();
        assertEquals(HIGH_PRIORITY, userPriority);
    }

    @Test
    void calculatePriority_forOldUser_ok() {
        Priority priority = priorityCalculator.calculatePriority(oldUser, bookProduct);
        String userPriority = priority.name();
        assertEquals(MEDIUM_PRIORITY, userPriority);
    }

    @Test
    void calculatePriority_forOldUserWithPremium_ok() {
        Priority priority = priorityCalculator.calculatePriority(oldUser, medicalProduct);
        String userPriority = priority.name();
        assertEquals(HIGH_PRIORITY, userPriority);
    }

    @Test
    void calculatePriority_notOk() {
        InvalidInputException exception = assertThrows(InvalidInputException.class,
                () -> priorityCalculator.calculatePriority(null, bookProduct));
        assertEquals("User can not be null", exception.getMessage());

    }
}
