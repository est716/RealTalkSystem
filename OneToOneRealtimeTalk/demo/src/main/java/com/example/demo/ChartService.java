package com.example.demo;

import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;

@Service
@Getter
@Setter
public class ChartService {
    private Room room;

    public ChartService(){
        this.room = new Room();
    }
}
