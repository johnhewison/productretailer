package com.products.demo;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dataloader")
public class DataLoader implements ApplicationRunner {
    private final ProductRepo productRepo;

    public DataLoader(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @Override
    public void run(ApplicationArguments args) {
        productRepo.save(Product.builder().name("a").stockLevel(5).minimumStockLevel(4).build());
        productRepo.save(Product.builder().name("b").stockLevel(8).minimumStockLevel(4).build());
        productRepo.save(Product.builder().name("c").stockLevel(2).minimumStockLevel(4).build());
        productRepo.save(Product.builder().name("d").stockLevel(0).minimumStockLevel(8).blocked(true).build());
        productRepo.save(Product.builder().name("e").stockLevel(1).minimumStockLevel(4).build());
    }
}

