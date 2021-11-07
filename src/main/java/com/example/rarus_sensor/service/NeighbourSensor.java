package com.example.rarus_sensor.service;

import com.example.rarus_sensor.dto.SensorInfo;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class NeighbourSensor {

    private String host;
    private int port;
    private boolean isConfigured = false;
    private boolean neighbourFindSuccess = false;

    public void updateInformation(Optional<SensorInfo> sensorInfo) {
        setConfigured(true);
        if (sensorInfo.isEmpty()) {
            return;
        }
        setHost(sensorInfo.get().getIp());
        setPort(sensorInfo.get().getPort());
        setNeighbourFindSuccess(true);
    }

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

    public boolean isNeighbourFindSuccess() {
        return neighbourFindSuccess;
    }

    public void setNeighbourFindSuccess(boolean neighbourFindSuccess) {
        this.neighbourFindSuccess = neighbourFindSuccess;
    }

}
