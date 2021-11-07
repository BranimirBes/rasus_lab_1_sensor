package com.example.rarus_sensor.service;

import org.springframework.stereotype.Component;

@Component
public class MySensorInfo {

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
