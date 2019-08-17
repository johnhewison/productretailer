package com.products.demo.audit;

import com.products.demo.Product;
import com.products.demo.ProductService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductAuditService {

    private static final String AUDIT_PRODUCTS_FILENAME = "./audit/products_";
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH:mm");
    private final ProductAuditRepo productAuditRepo;
    private final CsvWriter csvWriter;
    private final ProductService productService;

    public ProductAuditService(ProductAuditRepo productAuditRepo, CsvWriter csvWriter, ProductService productService) {
        this.productAuditRepo = productAuditRepo;
        this.csvWriter = csvWriter;
        this.productService = productService;
    }


    public void saveAudit() {
        saveAudit(productService.getAllProducts());
    }

    void saveAudit(List<Product> allProducts) {
        List<ProductAudit> productAudits = toProductAudits(allProducts);
        productAuditRepo.saveAll(productAudits);
    }

    List<ProductAudit> toProductAudits(List<Product> allProducts) {
        final LocalDateTime now = LocalDateTime.now();
        return allProducts.stream().map(product -> {
            return ProductAudit.builder()
                    .auditDateTime(now)
                    .productName(product.getName())
                    .productid(product.getId())
                    .stockLevel(product.getStockLevel())
                    .build();
        }).collect(Collectors.toList());
    }

    public File generateReport() {
        List<Product> allProducts = productService.getAllProducts();
        saveAudit(allProducts);
        return csvWriter.createCsvFile(createFilename(), allProducts);
    }

    private String createFilename() {
        return AUDIT_PRODUCTS_FILENAME + format.format(new Date()) + ".csv";
    }
}
