package com.ylqm.codeshelf.gateway.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ylqm.codeshelf.gateway.enums.exception.SysMsgEnum;
import com.ylqm.codeshelf.gateway.utils.XmlUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Slf4j
@Configuration
public class MyGlobalExceptionHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();


        if (response.isCommitted()) {
            return Mono.error(ex);
        }

        SortedMap<String, String> map = new TreeMap<>();
        if (ex instanceof CustomException) {
            map.put("code", SysMsgEnum.SYS_ERROR.getCode());
            map.put("msg", SysMsgEnum.SYS_ERROR.getMsg());
        } else if (ex instanceof Exception) {
            map.put("code", SysMsgEnum.SYS_IO_ERROR.getCode());
            map.put("msg", SysMsgEnum.SYS_IO_ERROR.getMsg());
        }

        response.getHeaders().setContentType(MediaType.APPLICATION_XML);

        return response
				.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            return bufferFactory.wrap(XmlUtils.mapToXml(map).getBytes());
        }));

    }
}


