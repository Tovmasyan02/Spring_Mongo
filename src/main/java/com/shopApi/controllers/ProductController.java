package com.shopApi.controllers;

import com.shopApi.entities.FeedBack;
import com.shopApi.entities.Product;
import com.shopApi.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productManagement/product")
public class ProductController {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Product>> findAllProducts() {
        List<Product> products=productService.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity addProduct(@RequestBody Product product) {
        productService.save(product);
        return new ResponseEntity(null,HttpStatus.OK);
    }

    @GetMapping("/brand/{brandName}")
    public ResponseEntity<List<? extends Object>> getProductsByBrandName(@PathVariable String brandName) {
        List<? extends Object> products=productService.getProductsByBrandName(brandName);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }
    @PostMapping("/{id}/feedBack/add")
    public ResponseEntity addFeedBack(@PathVariable Integer id,
                                      @RequestBody FeedBack feedBack) {
        this.productService.addFeedBack(id,feedBack);
        return new ResponseEntity(null,HttpStatus.OK);
    }
}
