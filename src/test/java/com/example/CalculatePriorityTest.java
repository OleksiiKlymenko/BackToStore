package com.example;

import com.example.model.Product;
import com.example.model.User;
import com.example.priority.CalculatePriority;
import com.example.priority.CalculatePriorityImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalculatePriorityTest {
    private static final String JOHN_NAME = "John";
    private static final String BOB_NAME = "Bob";
    private static final boolean PREMIUM = true;
    private static final boolean NOT_PREMIUM = false;
    private static final int JOHN_AGE = 33;
    private static final int BOB_AGE = 70;
    private static final String HIGH_PRIORITY = "HIGH";
    private static final String MEDIUM_PRIORITY = "MEDIUM";
    private User validUser;
    private User oldUser;
    private Product bookPoduct;
    private Product medicalPoduct;
    private CalculatePriority calculatePriority;

    @BeforeEach
    void setUp() {
        validUser = new User(JOHN_NAME, PREMIUM, JOHN_AGE);
        oldUser = new User(BOB_NAME, NOT_PREMIUM, BOB_AGE);
        bookPoduct = new Product("1", ProductCategory.BOOKS);
        medicalPoduct = new Product("2", ProductCategory.MEDICAL);
        calculatePriority = new CalculatePriorityImpl();
    }

    @Test
    void calculatePriority_high_ok() {
        Priority priority = calculatePriority.calculatePriority(validUser, bookPoduct);
        String userPriority = priority.name();
        Assertions.assertEquals(HIGH_PRIORITY, userPriority);
    }

    @Test
    void calculatePriority_medium_ok() {
        Priority priority = calculatePriority.calculatePriority(oldUser, bookPoduct);
        String userPriority = priority.name();
        Assertions.assertEquals(MEDIUM_PRIORITY, userPriority);
    }

    @Test
    void calculatePriority_medical_ok() {
        Priority priority = calculatePriority.calculatePriority(oldUser, medicalPoduct);
        String userPriority = priority.name();
        Assertions.assertEquals(HIGH_PRIORITY, userPriority);
    }

    @Test
    void calculatePriority_notOk() {
        Assertions.assertThrows(NullPointerException.class,
                () -> calculatePriority.calculatePriority(null, bookPoduct));
    }
}