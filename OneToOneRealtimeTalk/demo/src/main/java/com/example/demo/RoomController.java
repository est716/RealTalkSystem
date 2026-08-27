package com.example.demo;

import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@AllArgsConstructor
@NoArgsConstructor
public class RoomController {
    
    private ChatRoomService chatRoomService;

    @PostMapping("/createRoom")
    public ChatRoom createRoom(@RequestBody String roomName) {
        ChatRoom chatRoom = chatRoomService.createRoom(roomName);
        return chatRoom;
    }
}
