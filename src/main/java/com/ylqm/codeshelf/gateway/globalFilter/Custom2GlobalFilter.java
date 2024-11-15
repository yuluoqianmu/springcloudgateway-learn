package com.ylqm.codeshelf.gateway.globalFilter;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ylqm.codeshelf.gateway.enums.exception.SysMsgEnum;
import com.ylqm.codeshelf.gateway.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyRequestBodyGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
public class Custom2GlobalFilter implements GlobalFilter, Ordered {


    private final ModifyRequestBodyGatewayFilterFactory factory = new ModifyRequestBodyGatewayFilterFactory();


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ModifyRequestBodyGatewayFilterFactory.Config config = new ModifyRequestBodyGatewayFilterFactory.Config();
        config.setInClass(String.class);
        config.setOutClass(String.class);

        config.setRewriteFunction(new RewriteFunction() {
            @Override
            public Object apply(Object o, Object o2) {
                ServerWebExchange serverWebExchange = (ServerWebExchange) o;
                String oldBody = (String)o2;
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(oldBody, JsonObject.class);
                jsonObject.addProperty("global", "global");
                String newBody = gson.toJson(jsonObject);
                try {
                    int i = 1/0;
                } catch (Exception e) {
                    throw new CustomException(SysMsgEnum.SYS_ERROR);
                }
                // 对oldBody进行操作
                return Mono.just(newBody);
            }
        });

        // 获得路由
        Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        // 配置文件中配置的route的uri属性(匹配到的route),本例中是http://127.0.0.1:8001
        String uri = route.getUri().toString();

        ServerHttpRequest request = exchange.getRequest();
        // 请求路径中域名之后的部分,本例中是/api/name/get
        String path = request.getPath().toString();

        log.info("uri:{},path:{}", uri, path);

        return factory.apply(config).filter(exchange,chain);
    }

    @Override
    public int getOrder() {
        return -9999;
    }
}
