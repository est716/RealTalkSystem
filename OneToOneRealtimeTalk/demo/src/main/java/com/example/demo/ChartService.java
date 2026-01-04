package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;

@Service
@Getter
@Setter
public class ChartService {
    @Autowired
    private Room room; // can use dependency injection by autowired

    public ChartService(){
        // this.room = new Room();
    }
}
