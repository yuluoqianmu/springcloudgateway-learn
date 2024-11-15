package com.ylqm.codeshelf.gateway.service;

import org.springframework.web.server.ServerWebExchange;

public interface MyModifyRequestService {

    String decodeRequestBody(ServerWebExchange exchange, String body);

}
