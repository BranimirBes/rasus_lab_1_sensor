package com.example.rarus_sensor.scheduled;

import com.example.rarus_sensor.SensorReadingResponse;
import com.example.rarus_sensor.dto.SensorReadingDto;
import com.example.rarus_sensor.service.MySensorInfo;
import com.example.rarus_sensor.service.NeighbourSensor;
import com.example.rarus_sensor.service.SensorReadingFileService;
import com.example.rarus_sensor.service.SensorRequestReadingService;
import com.example.rarus_sensor.service.SensorRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class GenerateReadingsScheduledService {

    private static final Logger logger = Logger.getLogger(GenerateReadingsScheduledService.class.getName());
    private final SensorRequestReadingService sensorRequestReadingService;
    private final NeighbourSensor neighbourSensor;
    private final MySensorInfo mySensorInfo;
    private final SensorReadingFileService sensorReadingFileService;
    private final SensorRestService sensorRestService;

    @Autowired
    public GenerateReadingsScheduledService(
        SensorRequestReadingService sensorRequestReadingService,
        NeighbourSensor neighbourSensor,
        MySensorInfo mySensorInfo, SensorReadingFileService sensorReadingFileService,
        SensorRestService sensorRestService) {
        this.sensorRequestReadingService = sensorRequestReadingService;
        this.neighbourSensor = neighbourSensor;
        this.mySensorInfo = mySensorInfo;
        this.sensorReadingFileService = sensorReadingFileService;
        this.sensorRestService = sensorRestService;
    }

    @Scheduled(initialDelay = 10000, fixedDelay = 10000)
    public void generateReading() {
        SensorReadingDto sensorReadingDto = null;
        if (!neighbourSensor.isConfigured()) {
            return;
        }
        SensorReadingResponse mySensorReading = sensorReadingFileService.getSensorReading();

        SensorReadingResponse response;
        if (neighbourSensor.isNeighbourFindSuccess()) {
            try {
                response =
                    sensorRequestReadingService.getSensorReading(neighbourSensor.getHost(), neighbourSensor.getPort());
                sensorReadingDto = combineResults(response, mySensorReading);
            } catch (Exception e) {
                logger.info("Error getting sensor reading");
            }
        } else {
            sensorReadingDto = map(mySensorReading);
        }

        sensorRestService.saveSensorData(sensorReadingDto, mySensorInfo.getId());
    }

    private static SensorReadingDto combineResults(SensorReadingResponse response,
        SensorReadingResponse mySensorReading) {
        final Double temperature = (double) (response.getTemperature() + mySensorReading.getTemperature()) / 2;
        final Double pressure = (double) (response.getPressure() + mySensorReading.getPressure()) / 2;
        final Double humidity = (double) (response.getHumidity() + mySensorReading.getHumidity()) / 2;

        final Double co = getValue(response.getCo(), mySensorReading.getCo());
        final Double no2 = getValue(response.getNo2(), mySensorReading.getNo2());
        final Double so2 = getValue(response.getSo2(), mySensorReading.getSo2());

        SensorReadingDto sensorReadingDto = new SensorReadingDto();
        sensorReadingDto.setTemperature(temperature);
        sensorReadingDto.setPressure(pressure);
        sensorReadingDto.setHumidity(humidity);
        sensorReadingDto.setCo(co);
        sensorReadingDto.setNo2(no2);
        sensorReadingDto.setSo2(so2);

        return sensorReadingDto;
    }

    private static Double getValue(float responseValue, float myValue) {
        if (Float.compare(responseValue, 0) == 0 && Float.compare(myValue, 0) == 0) {
            return null;
        }

        if (Float.compare(responseValue, 0) == 0) {
            return (double) myValue;
        }

        return (double) (responseValue + myValue) / 2;
    }

    private static SensorReadingDto map(SensorReadingResponse response) {
        SensorReadingDto sensorReadingDto = new SensorReadingDto();
        sensorReadingDto.setTemperature((double) response.getTemperature());
        sensorReadingDto.setPressure((double) response.getPressure());
        sensorReadingDto.setHumidity((double) response.getHumidity());
        sensorReadingDto.setCo((double) response.getCo());
        sensorReadingDto.setNo2((double) response.getNo2());
        sensorReadingDto.setSo2((double) response.getSo2());

        return sensorReadingDto;
    }

}
