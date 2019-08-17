package com.products.demo;

import com.products.demo.audit.ProductAuditService;
import com.products.demo.audit.ProductServiceAuditException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final ProductAuditService productAuditService;

    public ProductController(ProductService productService, ProductAuditService productAuditService) {
        this.productService = productService;
        this.productAuditService = productAuditService;
    }

    @PutMapping(value = "/miniumstock/{productId}/{minimumstock}")
    ResponseEntity setMinimumStockLevel(@PathVariable long productId, @PathVariable int minimumstock) {
        productService.setMinimumStockLevel(productId, minimumstock);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/order/{productId}/{additionalVolume}")
    ResponseEntity orderProducts(@PathVariable long productId, @PathVariable int additionalVolume) {
        productService.orderProducts(productId, additionalVolume);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/block/{productId}")
    ResponseEntity blockProduct(@PathVariable long productId) {
        productService.blockProduct(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/unblock/{productId}")
    ResponseEntity unblockProduct(@PathVariable long productId) {
        productService.unblockProduct(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/audit", produces = "text/csv")
    public ResponseEntity audit() {
        try {
            File file = productAuditService.generateReport();

            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=" + file.getName())
                    .contentLength(file.length())
                    .contentType(MediaType.parseMediaType("text/csv"))
                    .body(new FileSystemResource(file));

        } catch (ProductServiceAuditException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to generate report", ex);
        }
    }
}
