package com.example.demo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ChartRecordOfRoom {
    private List<UserIDAndContextPair<String, String>> chartRecord;
}
