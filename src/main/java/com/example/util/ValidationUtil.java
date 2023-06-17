package com.example.util;

import com.example.exception.InvalidInputException;
import com.example.model.Product;
import com.example.model.User;

public class ValidationUtil {
    public static void validateNotNull(User user, Product product) {
        if (user == null || product == null) {
            String message = String.format("%s can not be null", user == null ? "User" : "Product");
            throw new InvalidInputException(message);
        }
    }
}
