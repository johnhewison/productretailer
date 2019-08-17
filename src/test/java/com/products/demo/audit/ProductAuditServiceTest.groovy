package com.products.demo.audit

import com.products.demo.Product
import com.products.demo.ProductService
import spock.lang.Specification

class ProductAuditServiceTest extends Specification {

    ProductAuditService productAuditService

    ProductAuditRepo productAuditRepo
    CsvWriter csvWriter
    ProductService productService

    void setup() {
        productAuditRepo = Mock()
        csvWriter = Mock()
        productService = Mock()
        productAuditService = new ProductAuditService(productAuditRepo, csvWriter, productService)
    }

    def "toProductAudits"() {
        given:
        def products = createProductList()
        when:
        def audits = productAuditService.toProductAudits(products)
        then:
        audits.size() == 2
        audits[0].productid == 1
        audits[0].productName == "a"
        audits[0].stockLevel == 4
    }

    def "generateReport"() {
        given:
        productService.getAllProducts() >> createProductList()
        when:
        productAuditService.generateReport()
        then:
        1 * productAuditRepo.saveAll(_)
        1 * csvWriter.createCsvFile(_, _)
    }

    private List<Product> createProductList() {
        Arrays.asList(Product.builder().id(1).name("a").stockLevel(4).build(),
                Product.builder().id(2).name("b").stockLevel(3).build())
    }
}
