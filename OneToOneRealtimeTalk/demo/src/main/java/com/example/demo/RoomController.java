package com.example.demo;

import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;





@RestController
@AllArgsConstructor
@NoArgsConstructor
public class RoomController {
    
    private ChatRoomService chatRoomService;

    @PostMapping("/createRoom")
    public ResponseEntity<ChatRoom> createRoom(@RequestBody String roomName) {
        ChatRoom chatRoom = chatRoomService.createRoom(roomName);
        return ResponseEntity.ok(chatRoom);
    }

    @GetMapping("/getRoomList")
    public ResponseEntity<List<ChatRoom>> getRoomList() {
        return ResponseEntity.ok(chatRoomService.getRoomList());
    }
    
    @GetMapping("/getChatRoomRecord/{roomId}")
    public ResponseEntity<List<ChatMessage>> getChatRoomRecord(@PathVariable String roomId) {
        return ResponseEntity.ok(chatRoomService.getChatRoomRecord(roomId));
    }
    
}
