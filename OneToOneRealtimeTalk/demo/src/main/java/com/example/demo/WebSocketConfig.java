package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import lombok.AllArgsConstructor;


@Configuration
@EnableWebSocket
@AllArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer{
    private final ChartService chartService;
    @Override
    public void registerWebSocketHandlers(@SuppressWarnings("null") WebSocketHandlerRegistry registry) {
        java.util.Objects.requireNonNull(registry, "WebSocketHandlerRegistry must not be null");
        registry.addHandler(new ChatWebSocketHandler(chartService), "/socket").setAllowedOrigins("*");
    }
    
}
