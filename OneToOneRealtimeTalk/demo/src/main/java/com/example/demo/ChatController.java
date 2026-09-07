package com.example.demo;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


@Controller
public class ChatController {
    
    private final MongoTemplate mongoTemplate;
    private final ChatRoomService chatRoomService;
    private final SimpMessagingTemplate messagingTemplate;

    ChatController(MongoTemplate mongoTemplate, ChatRoomService chatRoomService, SimpMessagingTemplate messagingTemplate) {
        this.mongoTemplate = mongoTemplate;
        this.chatRoomService = chatRoomService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat/{roomId}")
    public void sendMessage(@DestinationVariable String roomId, ChatMessage message){
        System.out.println("====== 成功進入 Controller！收到房間: " + roomId + "，內容: " + message);
        ChatMessage messageToSave = message.withRoomAndTimestamp(roomId, LocalDateTime.now());
        chatRoomService.touchRoomLastActive(roomId);
        ChatMessage savedMessage = mongoTemplate.save(messageToSave, "chat_messages_" + roomId);
        messagingTemplate.convertAndSend("/topic/" + roomId, savedMessage);
    }
    
}
