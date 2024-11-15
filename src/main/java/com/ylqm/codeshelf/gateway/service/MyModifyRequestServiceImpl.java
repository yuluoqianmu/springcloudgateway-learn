package com.ylqm.codeshelf.gateway.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

@Service
public class MyModifyRequestServiceImpl implements MyModifyRequestService{

    private static final Logger logger = LoggerFactory.getLogger(MyModifyRequestServiceImpl.class);

    @Override
    public String decodeRequestBody(ServerWebExchange exchange, String body) {

        Gson gson = new Gson();
        logger.info("body={}", body);

        JsonObject jsonObject = gson.fromJson(body, JsonObject.class);
        jsonObject.addProperty("gson", "gson");

        String json = gson.toJson(jsonObject);

        return json;
    }
}
