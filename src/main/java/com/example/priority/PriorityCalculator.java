package com.example.priority;

import com.example.Priority;
import com.example.model.Product;
import com.example.model.User;

public interface PriorityCalculator {
    public Priority calculatePriority(User user, Product product);
}
