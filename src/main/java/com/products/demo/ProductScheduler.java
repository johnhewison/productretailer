package com.products.demo;

import com.products.demo.audit.ProductAuditService;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Profile("scheduler")
public class ProductScheduler {

    private final ProductAuditService productAuditService;

    public ProductScheduler(ProductAuditService productAuditService) {
        this.productAuditService = productAuditService;
    }

    /**
     * Run at 02.00 every day
     * second, minute, hour, day of month, month, day(s) of week
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void takeStock() {
        productAuditService.saveAudit();
    }
}
