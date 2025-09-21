package com.example.demo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import lombok.Getter;

@Getter
public class Room {
    private String roomId;
    private List<User> users;
    private ChartRecordOfRoom record;

    public Room() {
        this.roomId = UUID.randomUUID().toString();
        this.users = new LinkedList<User>();
        this.record = new ChartRecordOfRoom(new ArrayList<UserIDAndContextPair<String, String>>());
    }

    public void addMessage(MessageBody message){
        this.record
            .getChartRecord()
            .add(new UserIDAndContextPair<String,String>(
                message.getUserId(), message.getContext()
                ));
    }

    public void addUser(User user) {
        if (users == null) {
            users = new LinkedList<User>();
        }
        if (!this.users.contains(user)) {
            users.add(user);
        }else{
            System.out.println("User already in room: " + user.getUserId());
        }
        
    }

    public void removeUser(User user){
        if(users.contains(user)){
            int index = users.indexOf(user);
            users.remove(index);
        }
    }
}
