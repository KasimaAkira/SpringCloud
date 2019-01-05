package com.epi.product_service.impl;

import com.epi.domain.Product;
import com.epi.product_service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Map<Integer,Product> daoMap=new HashMap<>();

    private final Logger logger= LoggerFactory.getLogger(getClass());

    static{
        Product p=new Product(1,"iphonex",9999,10);
        Product p1=new Product(2,"iphonexs max",11111,10);
        Product p2=new Product(3,"iphone5",8888,10);
        Product p3=new Product(4,"iphone6",7777,10);
        Product p4=new Product(5,"iphone7",6666,10);
        Product p5=new Product(6,"iphone8",5555,10);

        daoMap.put(p1.getId(),p1);
        daoMap.put(p2.getId(),p2);
        daoMap.put(p3.getId(),p3);
        daoMap.put(p4.getId(),p4);
        daoMap.put(p5.getId(),p5);

    }
    @Override
    public List<Product> listProduct() {

        Collection<Product> collection= daoMap.values();
        List<Product>list=new ArrayList<>(collection);
        return list;
    }

    @Override
    public Product findById(int id) {
        return daoMap.get(id);
    }
}
