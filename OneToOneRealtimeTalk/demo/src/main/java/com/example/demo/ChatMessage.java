package com.example.demo;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    @JsonCreator
    public ChatMessage(
        @JsonProperty("id") String id,                 // 前端沒傳時，Jackson 會傳進 null
        @JsonProperty("senderId") String senderId,
        @JsonProperty("senderName") String senderName,
        @JsonProperty("roomId") String roomId,         // 前端沒傳時，Jackson 會傳進 null
        @JsonProperty("content") String content,
        @JsonProperty("timestamp") LocalDateTime timestamp // 前端沒傳時，Jackson 會傳進 null
    ) {
        this.id = id;
        this.senderId = senderId;
        this.senderName = senderName;
        this.roomId = roomId;
        this.content = content;
        this.timestamp = timestamp;
    }
    

    public ChatMessage withRoomAndTimestamp(String roomId, LocalDateTime timestamp) {
        return new ChatMessage(this.id, this.senderId, this.senderName, roomId, this.content, timestamp);
    }
}
