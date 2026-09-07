package com.example.demo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class ChatRoomService {
    private final MongoTemplate mongoTemplate;
    private final ConcurrentHashMap<String, Long> lastUpdatedMap = new ConcurrentHashMap<>();
    public ChatRoomService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void touchRoomLastActive(String roomId) {
        long currentTime = System.currentTimeMillis();
        long lastUpdatedTime = lastUpdatedMap.getOrDefault(roomId, 0L);

        if (currentTime - lastUpdatedTime > 10000) {
            lastUpdatedMap.put(roomId, currentTime);
            Query query = new Query(Criteria.where("id").is(roomId));
            Update update = new Update().set("lastActiveAt", LocalDateTime.now());
            mongoTemplate.updateFirst(query, update, ChatRoom.class, "chat_rooms");
        }
    }

    public List<ChatRoom> getRoomList() {
        return mongoTemplate.findAll(ChatRoom.class, "chat_rooms");
    }

    public ChatRoom createRoom(String roomName) {
        ChatRoom chatRoom = new ChatRoom(java.util.UUID.randomUUID().toString(), roomName, LocalDateTime.now());
        return mongoTemplate.save(chatRoom);
    }

    public List<ChatMessage> getChatRoomRecord(String roomId) {
        return null;
    }
}
