package com.example.restapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

@Configuration

public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
    @Bean
    CommandLineRunner initDatabase(ProductRepo myProducts){
        return args -> {
            log.info("logging" +
                    myProducts.save(new Product("AirPods v3 2021","Headphones", 699.0,"headPhones")));
            log.info("logging" +
                    myProducts.save(new Product("iPhone 13","Cellular" ,4000.0,"headPhones")));
            log.info("logging" +
                    myProducts.save(new Product("MacBook pro 2021","laptops", 23000.0,"computer")));
        };
    }
}
