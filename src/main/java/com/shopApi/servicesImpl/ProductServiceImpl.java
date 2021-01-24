package com.shopApi.servicesImpl;

import com.shopApi.entities.FeedBack;
import com.shopApi.entities.Product;
import com.shopApi.repositories.ProductRepository;
import com.shopApi.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public void addFeedBack(Integer id, FeedBack feedBack) {
        Query query=Query.query(Criteria.where("id").is(id));
        Update update=new Update();
        update.push("feedBacks",feedBack);
        this.mongoTemplate.updateFirst(query,update,Product.class);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public List<Product> getProductsByBrandName(String productName) {
        ProjectionOperation po=Aggregation.project(Product.class).and("brand").toLower().as("brandToLower");
        MatchOperation mo=Aggregation.match(Criteria.where("brandToLower").is(productName.toLowerCase()));
        Aggregation aggregation=Aggregation.newAggregation(po,mo);
        AggregationResults<Product> results=mongoTemplate.aggregate(aggregation,"product",Product.class);
        return results.getMappedResults();
    }
}
