package com.epi.order_service.service.Imp;

import com.epi.order_service.domain.ProductOrder;
import com.epi.order_service.service.ProductClient;
import com.epi.order_service.service.ProductOrderService;
import com.epi.order_service.utils.JsonUtils;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class ProductOrderServiceImp implements ProductOrderService
{

    private final Logger logger= LoggerFactory.getLogger(getClass());
   // @Resource
   // private RestTemplate restTemplate;
    @Resource
    private ProductClient productClient;
    //@Resource
    //  private LoadBalancerClient loadBalancerClient;


    @Override
    public ProductOrder save(int userId, int productId) {

        String response=productClient.findById(productId);

        JsonNode jsonNode=JsonUtils.str2JsonNode(response);

       //Map<String,Object> productMap= restTemplate.getForObject("http://product-service/api/v1/product/find?id="+productId, Map.class);

        /*ServiceInstance instance=loadBalancerClient.choose("product-service");
        String url=String.format("http://%s:%s/api/v1/product/find?id="+productId,instance.getHost(),instance.getPort());
        RestTemplate restTemplate=new RestTemplate();
        Map<String,Object> productMap=restTemplate.getForObject(url,Map.class);*/

        logger.info("order-service save");
        ProductOrder productOrder=new ProductOrder();

        productOrder.setCreateTime(new Date());
        productOrder.setUserId(userId);
        productOrder.setTradeNo(UUID.randomUUID().toString());

        productOrder.setProductName(jsonNode.get("name").toString());
        productOrder.setPrice(Integer.parseInt(jsonNode.get("price").toString()));

        return productOrder;
    }
}
