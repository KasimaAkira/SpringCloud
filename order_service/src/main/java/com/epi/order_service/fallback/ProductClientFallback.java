package com.epi.order_service.fallback;

import com.epi.order_service.service.ProductClient;
import org.springframework.stereotype.Component;

@Component
public class ProductClientFallback implements ProductClient {

    @Override
    public String findById(int id) {
        System.out.println("Feign 调用 product-service异常");
        return null;
    }
}
