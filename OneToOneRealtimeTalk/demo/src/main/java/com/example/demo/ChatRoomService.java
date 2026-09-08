package com.example.demo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.data.domain.Sort;
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
        // 確保按照 timestamp 降序排列，並且在 timestamp 相同的情況下，按照 id 降序排列
        Sort sort = Sort.by(Sort.Order.desc("timestamp"), Sort.Order.desc("id"));
        // 查詢指定房間的聊天記錄，限制返回最新的 50 條消息
        Query query = new Query(Criteria.where("roomId").is(roomId));
        query.with(sort);
        query.limit(50);
        return mongoTemplate.find(query, ChatMessage.class, "chat_messages_" + roomId);
    }

    public void deleteRoom(String roomId) {
        // 刪除房間
        Query roomQuery = new Query(Criteria.where("id").is(roomId));
        mongoTemplate.remove(roomQuery, ChatRoom.class, "chat_rooms");

        // 刪除該房間的聊天記錄
        mongoTemplate.dropCollection("chat_messages_" + roomId);  
    }
}
