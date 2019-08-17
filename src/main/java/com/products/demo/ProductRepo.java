package com.products.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
 interface ProductRepo extends JpaRepository<Product, Long> {
}
