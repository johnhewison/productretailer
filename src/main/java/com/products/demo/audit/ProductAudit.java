package com.products.demo.audit;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Tolerate;
import org.springframework.data.annotation.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Builder
@Data
@Entity
@Immutable
@Table(name = "productaudit")
public class ProductAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "auditdatetime", updatable = false)
    private LocalDateTime auditDateTime;

    @Column(name = "productid", updatable = false)
    private Long productid;

    @Column(name = "productname", updatable = false)
    private String productName;

    @Column(name = "stocklevel", updatable = false)
    private int stockLevel;

    @Tolerate
    public ProductAudit() {
    }
}
