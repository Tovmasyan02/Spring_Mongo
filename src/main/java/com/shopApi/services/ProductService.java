package com.shopApi.services;

import com.shopApi.entities.FeedBack;
import com.shopApi.entities.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();
    void save(Product product);
    void addFeedBack(Integer id, FeedBack feedBack);
    List<Product> getProductsByBrandName(String productName);
}
