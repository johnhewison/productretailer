package com.products.demo.audit

import com.products.demo.Product
import spock.lang.Specification

class CsvWriterTest extends Specification {

    CsvWriter csvWriter

    void setup() {
        csvWriter = new CsvWriter()
    }

    def "toCsvLine"() {
        expect:
        "a;4" == csvWriter.toCsvLine(Product.builder().name("a").stockLevel(4).build())
        "b;3" == csvWriter.toCsvLine(Product.builder().name("b").stockLevel(3).build())
    }
}
