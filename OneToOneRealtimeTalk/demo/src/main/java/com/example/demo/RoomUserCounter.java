package com.example.demo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;


@Component
public class RoomUserCounter {
    private final Map<String, AtomicInteger> roomUserCount = new ConcurrentHashMap<>();
    private int currentCount;

    public RoomUserCounter() {
        this.currentCount = 0;
    }

    @EventListener 
    public void handleSubscribe(SessionSubscribeEvent event){
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String destination = headerAccessor.getDestination();
        
        if(destination != null && destination.startsWith("/topic/")){
            String roomId = destination.replace("/topic/", "");
            
            this.currentCount = roomUserCount.computeIfAbsent(roomId, k -> new AtomicInteger(0))
                                            .incrementAndGet();
        }
    }
    
    @EventListener 
    public void handleUnsubscribe(SessionSubscribeEvent event){
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String destination = headerAccessor.getDestination();
        
        if(destination != null && destination.startsWith("/topic/")){
            String roomId = destination.replace("/topic/", "");
            roomUserCount.computeIfPresent(roomId, (k, count) -> {
                return count.decrementAndGet() > 0 ? count : null;
            });
        }
    }

    public int getCurrentCount() {
        return currentCount;
    }
}   
