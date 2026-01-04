package com.example.demo;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Component
@AllArgsConstructor
@Getter
@Setter
public class ChartRecordOfRoom {
    private List<UserIDAndContextPair<String, String>> chartRecord;
}
