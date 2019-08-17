package com.products.demo


import spock.lang.Specification

class ProductServiceTest extends Specification {

    ProductRepo productRepo

    ProductService productService

    void setup() {
        productRepo = Mock()
        productService = new ProductService(productRepo)
    }

    def "orderProducts"() {
        given:
        Product product = Product.builder().id(1).stockLevel(4).build()
        productRepo.findById(1) >> Optional.of(product)
        when:
        productService.orderProducts(1, 5)
        then:
        product.stockLevel == 9
    }

    def "orderProducts no additionalVolume"() {
        given:
        Product product = Product.builder().id(1).build()
        productRepo.findById(1) >> Optional.of(product)
        when:
        productService.orderProducts(1, 0)
        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "Additional Volume must be greater than 0"
    }

    def "orderProducts blocked"() {
        given:
        Product product = Product.builder().id(1).stockLevel(2).blocked(true).build()
        productRepo.findById(1) >> Optional.of(product)
        when:
        productService.orderProducts(1, 2)
        then:
        def exception = thrown(ProductBlockedException)
        exception.message == "Product 1 is blocked"
    }

    def "block product"() {
        given:
        Product product = Product.builder().id(1).blocked(false).build()
        productRepo.findById(1) >> Optional.of(product)
        when:
        productService.blockProduct(1)
        then:
        product.blocked
    }

    def "findProduct"() {
        given:
        Product product = Product.builder().id(1).build()
        productRepo.findById(1) >> Optional.of(product)
        when:
        def productFromDB = productService.findProduct(1)
        then:
        productFromDB == product
    }

    def "findProduct not found"() {
        given:
        productRepo.findById(1) >> Optional.empty()
        when:
        productService.findProduct(1)
        then:
        def exception = thrown(ProductNotFoundException)
        exception.message == "Product 1 not found"
    }
}
