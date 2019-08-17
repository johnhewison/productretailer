package com.products.demo.audit;

public class ProductServiceAuditException extends RuntimeException {
    public ProductServiceAuditException(String message, Throwable cause) {
        super(message, cause);
    }
}
