package com.example.restapp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

// ProductDAL
public interface ProductRepo extends JpaRepository<Product,Long> {

//    public Product (@RequestParam Optional<Product> id){
//        return "ID: " + id.orElseGet(() -> "not provided");
//    }

    @Query("select p from Product p where p.productName = :title")
    Product findByTitle(String title);
}