package com.example.demo;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


@Controller
public class ChatController {
    
    private final MongoTemplate mongoTemplate;
    private final ChatRoomService chatRoomService;

    ChatController(MongoTemplate mongoTemplate, ChatRoomService chatRoomService) {
        this.mongoTemplate = mongoTemplate;
        this.chatRoomService = chatRoomService;
    }

    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/{roomId}")
    public ChatMessage sendMessage(@DestinationVariable String roomId, ChatMessage message){
        ChatMessage messageToSave = message.withRoomAndTimestamp(roomId, LocalDateTime.now());
        chatRoomService.touchRoomLastActive(roomId);
        return mongoTemplate.save(messageToSave, "chat_messages_" + roomId);
    }
    
}
