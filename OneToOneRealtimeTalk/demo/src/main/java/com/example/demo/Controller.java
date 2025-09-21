package com.example.demo;

import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@AllArgsConstructor
@RequestMapping("/api")
@Getter
public class Controller {
    private final ChartService chartService;

    @GetMapping("/createChartService")
    public Map<String, String> createChartService() {
        if (this.chartService == null) {
            return Map.of("status", "error", "message", "ChartService not initialized");
        }
        System.out.println("ChartService created");
        return Map.of("status", "success");
    }

    @PostMapping("/EnterRoom")
    public Map<String, String> enterRoom(@RequestBody User user) {
        Room room = this.chartService.getRoom();
        if (room == null){
            return Map.of("status", "Room error", "message", "Room not initialized");
        }
        if (user == null || user.getUserId() == null || user.getUserId().isEmpty()) {
            return Map.of("status", "User error", "message", "Invalid user");
        }
        room.addUser(user);
        System.out.println("User " + user.getUserId() + " entered room " + room.getRoomId());
        return Map.of("status", "success", "roomId", room.getRoomId());
    }

    @PostMapping("/postMessage")
    public void postMessage(@RequestBody MessageBody messageBody) {
        Room room = this.chartService.getRoom();
        if (room.getRoomId().equals(messageBody.getRoomId()) 
            && room.getUsers().contains(new User(messageBody.getUserId()))) {
            room.addMessage(messageBody);
        }else{
            System.out.println("Message posting failed: User not in room");
        }
        System.out.println("Message posted to room " + messageBody.getRoomId());
        System.out.println("Messages: " + messageBody.getContext());
    }
    

    @GetMapping("/{roomId}")
    public Map<String, Object> getMessageBody(@PathVariable("roomId") String roomId) {
        Room room = this.chartService.getRoom();
        if (room != null && room.getRoomId().equals(roomId)) {
            System.out.println("Returning MessageBody for room: " + roomId);
            return Map.of("roomId", roomId, "record", room.getRecord());
        }
        return Map.of("roomId", roomId, "record", null);
    }

}
