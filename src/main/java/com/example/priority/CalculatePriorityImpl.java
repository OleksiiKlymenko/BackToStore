package com.example.priority;

import com.example.Priority;
import com.example.ProductCategory;
import com.example.model.Product;
import com.example.model.User;

public class CalculatePriorityImpl implements CalculatePriority {
    @Override
    public Priority calculatePriority(User user, Product product) {
        if (user == null) {
            throw new NullPointerException("User is null");
        }
        if (product == null) {
            throw new NullPointerException("Product is null");
        }

        boolean premium = user.isPremium();
        int age = user.getAge();
        ProductCategory productCategory = product.getCategory();

        if (premium) {
            return Priority.HIGH;
        } else if (age >= 70) {
            if (productCategory == ProductCategory.MEDICAL) {
                return Priority.HIGH;
            } else {
                return Priority.MEDIUM;
            }
        } else {
            return Priority.FIFO;
        }
    }
}
