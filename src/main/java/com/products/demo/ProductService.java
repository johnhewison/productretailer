package com.products.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
@Service
@Transactional
public class ProductService {

    private final ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public void setMinimumStockLevel(long productId, int minimumstock) {
        Assert.isTrue(minimumstock > 0, "Minimum Stock level cannot be negative");
        Product product = findProduct(productId);
        product.setMinimumStockLevel(minimumstock);
        log.info("setMinimumStockLevel productId:{} minimumstock:{}", productId, minimumstock);
    }

    public void orderProducts(long productId, int additionalVolume) {
        Assert.isTrue(additionalVolume > 0, "Additional Volume must be greater than 0");
        Product product = findProduct(productId);
        if (product.isBlocked()) {
            throw new ProductBlockedException("Product " + productId + " is blocked");
        }
        product.setStockLevel(product.getStockLevel() + additionalVolume);
        log.info("orderProducts productId:{} additionalVolume:{}", productId, additionalVolume);
    }

    public void blockProduct(long productId) {
        productRepo.findById(productId).ifPresent(product -> {
            product.setBlocked(true);
            log.info("product {} blocked", product);
        });
    }

    public void unblockProduct(long productId) {
        productRepo.findById(productId).ifPresent(product -> {
            product.setBlocked(false);
            log.info("product {} unblocked", product);
        });
    }

    Product findProduct(long productId) {
        return productRepo.findById(productId).orElseThrow(() ->
                new ProductNotFoundException("Product " + productId + " not found"));
    }

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }
}
