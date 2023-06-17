package com.example.service;

import static com.example.util.ValidationUtil.validateNotNull;

import com.example.model.Product;
import com.example.model.User;
import com.example.priority.PriorityCalculator;
import com.example.priority.PriorityCalculatorImpl;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class BackToStockServiceImpl implements BackToStockService {
    private Map<Product, List<User>> subscriptions;
    private PriorityCalculator priorityCalculator;

    public BackToStockServiceImpl() {
        this.subscriptions = new HashMap<>();
        this.priorityCalculator = new PriorityCalculatorImpl();
    }

    @Override
    public void subscribe(User user, Product product) {
        validateNotNull(user, product);
        List<User> subscribedUsers = subscriptions.getOrDefault(product, new ArrayList<>());
        subscribedUsers.add(user);
        subscriptions.put(product, subscribedUsers);
    }

    @Override
    public List<User> subscribedUsers(Product product) {
        return subscriptions.getOrDefault(product, new ArrayList<>());
    }

    @Override
    public List<User> notifyUsers(Product product) {
        PriorityQueue<User> priorityQueue = new PriorityQueue<>(Comparator
                .comparing(user -> priorityCalculator.calculatePriority(user, product)));

        List<User> subscribedUsers = subscribedUsers(product);
        for (User user : subscribedUsers) {
            priorityQueue.offer(user);
        }

        List<User> notifiedUser = new ArrayList<>();
        while (!priorityQueue.isEmpty()) {
            User userFromPriorityQueue = priorityQueue.poll();
            if (!notifiedUser.contains(userFromPriorityQueue)) {
                notifiedUser.add(userFromPriorityQueue);
                System.out.println("Send notification to user " + userFromPriorityQueue.getName());
            }
        }
        return notifiedUser;
    }
}

