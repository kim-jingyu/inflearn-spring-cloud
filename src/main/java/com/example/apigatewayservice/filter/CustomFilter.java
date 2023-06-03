package com.example.apigatewayservice.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {

    public CustomFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        // Custom pre filter
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();  // Netty 비동기 통신에서는 ServerHttpRequest 객체 사용
            ServerHttpResponse response = exchange.getResponse();

            log.info("Custom PRE filter - requestId = {}", request.getId());

            // Custom post filter
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {    // Mono -> WebFlux
                log.info("Custom POST filter - response status code = {}", response.getStatusCode());
            }));
        };
    }

    public static class Config {
        // configuration 정보를 집어넣을 수 있음.
    }

}
