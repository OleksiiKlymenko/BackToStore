package com.example.service;

import com.example.model.Product;
import com.example.model.User;
import java.util.List;

public interface BackToStockService {

    void subscribe(User user, Product product);

    List<User> subscribedUsers(Product product);

    List<User> notifyUsers(Product product);

}
