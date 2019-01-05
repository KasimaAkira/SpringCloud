package com.epi.product_service;

import com.epi.domain.Product;
import org.apache.catalina.LifecycleState;

import java.util.List;

public interface ProductService {
    List<Product> listProduct();

    Product findById(int id);

}
