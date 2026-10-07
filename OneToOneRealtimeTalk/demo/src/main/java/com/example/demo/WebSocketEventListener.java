package com.example.demo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

public class WebSocketEventListener{

    private SimpMessagingTemplate messageTemplate;
    // 使用 ConcurrentHashMap 記錄房間人數，使用 AtomicInteger 保證讀寫不可被分割
    private final Map<String, AtomicInteger> roomUserCount = new ConcurrentHashMap<>();

    @EventListener
    public void handleSubscribeEvent(SessionSubscribeEvent event){
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String destination = headerAccessor.getDestination();
        if(destination != null && destination.startsWith("/topic/")){
            String roomId = destination.replace("/topic/", "");

            int currentCount = roomUserCount.computeIfAbsent(roomId, k -> new AtomicInteger(0)).incrementAndGet();
        }

    }
}