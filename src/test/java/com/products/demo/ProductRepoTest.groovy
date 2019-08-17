package com.products.demo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

@DataJpaTest
class ProductRepoTest extends Specification {

    @Autowired
    ProductRepo productRepo

    def "test save"() {
        given:
        def product = Product.builder().name("a").stockLevel(5).build()
        when:
        productRepo.save(product)
        then:
        productRepo.count() == 1
        def savedProduct = productRepo.getOne(product.id)
        savedProduct != null
        with(savedProduct){
            name == "a"
            stockLevel == 5
            blocked == false
        }

    }
}
