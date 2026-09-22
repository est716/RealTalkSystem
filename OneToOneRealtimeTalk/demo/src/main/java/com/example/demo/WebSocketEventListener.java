package com.example.demo;


public class WebSocketEventListener{

    private SimpMessagingTemplate messageTemplate;
    // 使用 ConcurrentHashMap 記錄房間人數，使用 AtomicInteger 保證讀寫不可被分割
    private final Map<String, AtomicInteger> roomUserCount = new concurrentHashMap<>();

}