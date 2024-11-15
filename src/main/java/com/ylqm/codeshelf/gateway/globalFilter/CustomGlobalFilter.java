package com.ylqm.codeshelf.gateway.globalFilter;

import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.*;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Slf4j
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    private final DataBufferFactory dataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {


        ServerHttpRequestDecorator decorator = new ServerHttpRequestDecorator(exchange.getRequest()) {
            @Override
            public Flux<DataBuffer> getBody() {
                Flux<DataBuffer> body = super.getBody();
                InputStreamHolder holder = new InputStreamHolder();
                body.subscribe(buffer -> holder.inputStream = buffer.asInputStream());

                InputStream inputStream = holder.inputStream;
                String text = new BufferedReader(
                        new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                        .lines()
                        .collect(Collectors.joining("\n"));

                if (null != holder.inputStream) {
                    DataBuffer dataBuffer = dataBufferFactory.allocateBuffer(1024);
                    dataBuffer.write(text.getBytes(StandardCharsets.UTF_8));
                    return Flux.just(dataBuffer);
                } else {
                    return body;
                }

            }
        };

        return chain.filter(exchange.mutate().request(decorator).build());

    }

    @Override
    public int getOrder() {
        return -1;
    }

    private class InputStreamHolder {

        InputStream inputStream;
    }


}
