package com.products.demo


import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest extends Specification {
    @Autowired
    MockMvc mvc

    @SpringBean
    ProductService productService = Mock()

    def "orderProducts"() {
        when:
        mvc.perform(put("/product/order/1/1"))
                .andExpect(status().is2xxSuccessful()).andReturn()
        then:
        1 * productService.orderProducts(1, 1)
    }

    def "blockBlack"() {
        when:
        mvc.perform(put("/product/block/1"))
                .andExpect(status().is2xxSuccessful()).andReturn()
        then:
        1 * productService.blockProduct(1)
    }
}
