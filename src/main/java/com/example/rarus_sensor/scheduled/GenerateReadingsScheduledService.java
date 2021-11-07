package com.example.rarus_sensor.scheduled;

import com.example.rarus_sensor.SensorReadingResponse;
import com.example.rarus_sensor.SensorRequestReadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Component
public class GenerateReadingsScheduledService {
    private static final Logger logger = Logger.getLogger(GenerateReadingsScheduledService.class.getName());
    private final SensorRequestReadingService sensorRequestReadingService;

    @Autowired
    public GenerateReadingsScheduledService(
        SensorRequestReadingService sensorRequestReadingService) {
        this.sensorRequestReadingService = sensorRequestReadingService;
    }

    @Scheduled(initialDelay = 10000, fixedDelay = 10000)
    public void generateReading() {
        try {
            SensorReadingResponse response = sensorRequestReadingService.getSensorReading("127.0.0.1", 8080);
            logger.info("SensorReading response is " + response.getCo());
        } catch (Exception e) {
            logger.info("Error getting sensor reading");
        }
    }

}
