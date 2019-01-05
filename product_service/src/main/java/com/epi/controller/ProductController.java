package com.epi.controller;


import com.epi.domain.Product;
import com.epi.product_service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController
{

    @Value("${server.port}")
    private String port;

    @Resource
    private ProductService productService;


    @GetMapping("list")
    public Object list()
    {
        return productService.listProduct();
    }

    @RequestMapping("find")
    public Object findById(@RequestParam("id") int id)
    {
        Product product=productService.findById(id);
        Product result=new Product();
        BeanUtils.copyProperties(product,result);

        result.setName(result.getName()+"data from"+port);
        return result;
    }

}
