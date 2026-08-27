package com.example.demo;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * ChatMessage
 */
@Document
@CompoundIndex(name = "roomId_timestamp_idx", def = "{'roomId': 1, 'timestamp': -1}")
public record ChatMessage(
    @Id String id,
    String senderId,
    String senderName,
    String roomId,
    String content,
    LocalDateTime timestamp
){
    public ChatMessage withRoomAndTimestamp(String roomId, LocalDateTime timestamp) {
        return new ChatMessage(this.id, this.senderId, this.senderName, roomId, this.content, timestamp);
    }
}
