package com.epi.order_service.service;

import com.epi.order_service.domain.ProductOrder;

public interface ProductOrderService {

    public ProductOrder save(int userId,int productId);

}
