package com.example.priority;

import com.example.Priority;
import com.example.ProductCategory;
import com.example.exception.InvalidInputException;
import com.example.model.Product;
import com.example.model.User;
import static com.example.util.ValidationUtil.validateNotNull;

public class PriorityCalculatorImpl implements PriorityCalculator {
    @Override
    public Priority calculatePriority(User user, Product product) {
        validateNotNull(user, product);
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
