package com.example.priority;

import com.example.Priority;
import com.example.ProductCategory;
import com.example.model.Product;
import com.example.model.User;

public class PriorityCalculatorImpl implements PriorityCalculator {
    @Override
    public Priority calculatePriority(User user, Product product) {
        if (user == null) {
            throw new NullPointerException("User is null");
        }
        if (product == null) {
            throw new NullPointerException("Product is null");
        }

        ProductCategory productCategory = product.getCategory();

        if (user.isPremium()) {
            return Priority.HIGH;
        }
        if (user.getAge() >= 70) {
            return productCategory == ProductCategory.MEDICAL
                    ? Priority.HIGH : Priority.MEDIUM;
        } else {
            return Priority.FIFO;
        }
    }
}
