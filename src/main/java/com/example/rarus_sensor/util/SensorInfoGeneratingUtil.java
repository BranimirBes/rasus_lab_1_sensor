package com.example.rarus_sensor.util;

import com.example.rarus_sensor.dto.SensorInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SensorInfoGeneratingUtil {

    @Value("${server.port}")
    private int port;

    public SensorInfo generateSensorInfo() {
        SensorInfo sensorInfo = new SensorInfo();

        sensorInfo.setLatitude(generateLatitude());
        sensorInfo.setLongitude(generateLongitude());
        sensorInfo.setIp("127.0.0.1");
        sensorInfo.setPort(port);

        return sensorInfo;
    }

    private static double generateLatitude() {
        double min = 45.75;
        double toAdd = Math.random() / 10;
        return min + toAdd;
    }

    private static double generateLongitude() {
        double min = 15.87;
        double toAdd = (int) (Math.random() * 13) / (double) 100;
        return min + toAdd;
    }

}
