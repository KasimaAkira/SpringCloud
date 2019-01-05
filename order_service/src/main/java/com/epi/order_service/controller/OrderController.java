package com.epi.order_service.controller;



import com.alibaba.dubbo.common.utils.StringUtils;
import com.epi.order_service.service.ProductOrderService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController
{
    @Resource
    private ProductOrderService productOrderService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping("save")
    @HystrixCommand(fallbackMethod = "saveOrderFail")
    public Object save(@RequestParam("user_id")int userId, @RequestParam("product_id")int productId, HttpServletRequest request)
    {
        String token=request.getHeader("token");
        String cookie=request.getHeader("cookie");

        System.out.println("token"+token);
        System.out.println("cookie"+cookie);

        Map<String,Object> data=new HashMap<>();
        data.put("code",0);
        data.put("data",productOrderService.save(userId,productId));

       return data;
    }
    private Object saveOrderFail(int userId,int productId,HttpServletRequest request)
    {
        //监控报警
        String saveOrderKye="save-order";
        String sendValue=redisTemplate.opsForValue().get(saveOrderKye);
        String ip=request.getLocalAddr();

        new Thread( ()->{
            if(StringUtils.isBlank(sendValue))
            {
                System.out.println("紧急短信：用户下单失败请查找原因，IP地址为 "+ip);
                //发送http请求,调用短信服务
                redisTemplate.opsForValue().set(saveOrderKye,"save-order-fail",20, TimeUnit.SECONDS);
            }
            else
            {
                System.out.println("20s后再试");
            }

        }).start();

        Map<String,Object> msg =new HashMap<>();
        msg.put("code",-1);
        msg.put("msg","Hystrix降级");
        return msg;
    }
}
