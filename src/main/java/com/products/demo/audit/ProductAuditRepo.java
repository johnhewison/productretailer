package com.products.demo.audit;

import org.springframework.data.jpa.repository.JpaRepository;

interface ProductAuditRepo extends JpaRepository<ProductAudit, Long> {
}
