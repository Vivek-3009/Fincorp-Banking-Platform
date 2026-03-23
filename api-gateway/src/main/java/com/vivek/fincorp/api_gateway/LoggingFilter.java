package com.vivek.fincorp.api_gateway;

import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class LoggingFilter implements WebFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // PRE-FILTER: Logic before the request reaches the Controller/Functional Route
        System.out.println(">>> Incoming WebFlux Request: " + exchange.getRequest().getURI());
        
        return chain.filter(exchange)
            .then(Mono.fromRunnable(() -> {
                // POST-FILTER: Logic after the response is processed
                System.out.println(">>> Response Status: " + exchange.getResponse().getStatusCode());
            }));
    }

    @Override
    public int getOrder() {
        // High priority (runs early)
        return -1;
    }
}