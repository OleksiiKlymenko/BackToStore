package com.example;

import com.example.model.Product;
import com.example.model.User;
import com.example.service.BackToStockService;
import com.example.service.BackToStockServiceImpl;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        BackToStockService backToStockService = new BackToStockServiceImpl();

        Product product1 = new Product("P1", ProductCategory.MEDICAL);
        Product product2 = new Product("P2", ProductCategory.BOOKS);

        User user1 = new User("John", true, 50);
        User user2 = new User("Alice", false, 75);
        User user3 = new User("Bob", false, 30);

        backToStockService.subscribe(user3, product2);
        backToStockService.subscribe(user1, product2);
        backToStockService.subscribe(user2, product2);

        List<User> users = backToStockService.notifyUsers(product2);
        for (User user : users) {
            System.out.println(user.getName());
        }
    }
}
