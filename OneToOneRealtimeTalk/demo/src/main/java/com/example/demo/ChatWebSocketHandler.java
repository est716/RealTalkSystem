package com.example.demo;

import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final CopyOnWriteArrayList<WebSocketSession> sessions;
   
    private final ChartService chartService;
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ChatWebSocketHandler(ChartService chartService) {
        this.chartService = chartService;
        this.sessions = new CopyOnWriteArrayList<>();
    }

    @Override
    public void afterConnectionClosed(@SuppressWarnings("null") WebSocketSession session, @SuppressWarnings("null") CloseStatus status) throws Exception {
        java.util.Objects.requireNonNull(session, "WebSocketSession must not be null");
        java.util.Objects.requireNonNull(status, "CloseStatus must not be null");
        sessions.remove(session);
        System.out.println("Connection closed: " + session.getId());
    }

    @Override
    public void afterConnectionEstablished(@SuppressWarnings("null") WebSocketSession session) throws Exception {
        java.util.Objects.requireNonNull(session, "WebSocketSession must not be null");
        sessions.add(session);
        System.out.println("Connection established: " + session.getId());
    }

    @Override
    protected void handleTextMessage(@SuppressWarnings("null") WebSocketSession session, @SuppressWarnings("null") TextMessage message) throws Exception {
        java.util.Objects.requireNonNull(session, "WebSocketSession must not be null");
        java.util.Objects.requireNonNull(message, "TextMessage must not be null");
        System.out.println("Received message: " + message.getPayload());
        MessageBody msgBody = objectMapper.readValue(message.getPayload(), MessageBody.class);
        chartService.getRoom().addMessage(msgBody);
        for (WebSocketSession s : sessions) {
            if (s.isOpen()) {
                s.sendMessage(message);// message is json format
            }
        }
    }

    
    
}
