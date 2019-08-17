package com.products.demo;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public void orderProducts(long productId, int additionalVolume) {
        Assert.isTrue(additionalVolume > 0, "Additional Volume must be greater than 0");
        Product product = findProduct(productId);
        if (product.isBlocked()) {
            throw new ProductBlockedException("Product " + productId + " is blocked");
        }
        product.setStockLevel(product.getStockLevel() + additionalVolume);
    }

    public void blockProduct(Long productId) {
        productRepo.findById(productId).ifPresent(product -> {
            product.setBlocked(true);
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
