package com.example.rarus_sensor.service;

import com.example.rarus_sensor.SensorReadingResponse;
import com.example.rarus_sensor.grpc.RasusSensorClient;
import org.springframework.stereotype.Service;

@Service
public class SensorRequestReadingService {

    public SensorReadingResponse getSensorReading(String host, int port) throws InterruptedException {
        RasusSensorClient client = new RasusSensorClient(host, port);
        SensorReadingResponse response = client.requestSensorReading();
        client.stop();
        return response;
    }
}
