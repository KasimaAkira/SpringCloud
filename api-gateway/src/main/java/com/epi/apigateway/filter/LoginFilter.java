package com.epi.apigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
public class LoginFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 4;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext requestContext=RequestContext.getCurrentContext();
        HttpServletRequest request=requestContext.getRequest();

        //System.out.println(request.getRequestURI());
        //System.out.println(request.getRequestURL());
        if("/apigateway/order/api/v1/order/save".equalsIgnoreCase(request.getRequestURI()))
        {
            return true;
        }
        else if("/apigateway/product/api/v1/product/list".equalsIgnoreCase(request.getRequestURI()))
        {
            return true;
        }
        else if("/apigateway/product/api/v1/product/find".equalsIgnoreCase(request.getRequestURI()))
        {
            return true;
        }
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        //jwt
        RequestContext requestContext=RequestContext.getCurrentContext();
        HttpServletRequest request=requestContext.getRequest();

        //token对象
        String token=request.getHeader("token");
        if(StringUtils.isBlank(token))
        {
            token=request.getParameter("token");
        }
        if(StringUtils.isBlank(token))
        {
            requestContext.setSendZuulResponse(false);
            int unauthorized = HttpStatus.SC_UNAUTHORIZED;
            requestContext.setResponseStatusCode(unauthorized);
        }
        return null;
    }
}
