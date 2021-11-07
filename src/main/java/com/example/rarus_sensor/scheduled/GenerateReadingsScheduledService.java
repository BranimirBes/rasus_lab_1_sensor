package com.example.rarus_sensor.scheduled;

import com.example.rarus_sensor.SensorReadingResponse;
import com.example.rarus_sensor.service.NeighbourSensor;
import com.example.rarus_sensor.service.SensorRequestReadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class GenerateReadingsScheduledService {
    private static final Logger logger = Logger.getLogger(GenerateReadingsScheduledService.class.getName());
    private final SensorRequestReadingService sensorRequestReadingService;
    private final NeighbourSensor neighbourSensor;

    @Autowired
    public GenerateReadingsScheduledService(
        SensorRequestReadingService sensorRequestReadingService,
        NeighbourSensor neighbourSensor) {
        this.sensorRequestReadingService = sensorRequestReadingService;
        this.neighbourSensor = neighbourSensor;
    }

    @Scheduled(initialDelay = 10000, fixedDelay = 10000)
    public void generateReading() {
        if (!neighbourSensor.isConfigured()) {
            return;
        }

        SensorReadingResponse response;
        if (neighbourSensor.isNeighbourFindSuccess()) {
            try {
                response = sensorRequestReadingService.getSensorReading(neighbourSensor.getHost(), neighbourSensor.getPort());
            } catch (Exception e) {
                logger.info("Error getting sensor reading");
            }
        }
    }

}
