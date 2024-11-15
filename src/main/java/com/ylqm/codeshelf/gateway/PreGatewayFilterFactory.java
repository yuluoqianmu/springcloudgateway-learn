package com.ylqm.codeshelf.gateway;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;


import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

//@Component
public class PreGatewayFilterFactory extends AbstractGatewayFilterFactory<PreGatewayFilterFactory.Config> {

    public PreGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        // grab configuration from Config object
        return (exchange, chain) -> {
            //If you want to build a "pre" filter you need to manipulate the
            //request before calling chain.filter
            Flux<DataBuffer> data = exchange.getRequest().getBody();


            data.map(buffer -> {
                byte[] bytes = new byte[buffer.readableByteCount()];
                buffer.read(bytes);
                DataBufferUtils.release(buffer);
                String bodyJson = new String(bytes, Charset.forName("UTF-8"));
                return bytes;
            });

            ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
            builder.header("test", "123");
            try {
                builder.uri(new URI("123"));
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }

            //use builder to manipulate the request
            return chain.filter(exchange.mutate().request(builder.build()).build());
        };
    }

    public static class Config {
        //Put the configuration properties for your filter here
    }
}
