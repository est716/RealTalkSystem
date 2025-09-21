package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserIDAndContextPair<ID, Context> {
    private ID userId;
    private Context context;
}
