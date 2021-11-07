package com.example.rarus_sensor.service;

import org.springframework.stereotype.Component;

@Component
public class NeighbourSensor {

    private String host = "127.0.0.1";
    private int port = 8080;
    private boolean isConfigured = true;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isConfigured() {
        return isConfigured;
    }

    public void setConfigured(boolean configured) {
        isConfigured = configured;
    }

}
