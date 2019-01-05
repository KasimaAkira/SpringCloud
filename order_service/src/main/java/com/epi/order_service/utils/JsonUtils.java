package com.epi.order_service.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtils {

    private static final ObjectMapper objectMapper=new ObjectMapper();

    //Json字符串转JsonNode对象方法

    public static JsonNode str2JsonNode(String str)
    {
        try {
            return  objectMapper.readTree(str);
        } catch (IOException e) {
            return null;
        }

    }
}
