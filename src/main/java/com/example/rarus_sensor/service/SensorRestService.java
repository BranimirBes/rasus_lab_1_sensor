package com.example.rarus_sensor.service;

import com.example.rarus_sensor.dto.SensorInfo;
import com.example.rarus_sensor.dto.SensorReadingDto;
import com.example.rarus_sensor.exception.RegistrationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Optional;

@Service
public class SensorRestService {

    private final RestTemplate restTemplate;
    private final MySensorInfo mySensorInfo;

    @Autowired
    public SensorRestService(RestTemplateBuilder builder, MySensorInfo mySensorInfo) {
        this.restTemplate = builder
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .build();
        this.mySensorInfo = mySensorInfo;
    }

    public long registerSensor(SensorInfo sensorInfo) {
        final ResponseEntity<Void> response =
            restTemplate.postForEntity("http://localhost:8080/sensor/", sensorInfo, Void.class);

        if (Objects.equals(response.getStatusCode(), HttpStatus.NO_CONTENT)) {
            throw new RegistrationFailedException("Failed to register to server.");
        }

        return getIdFromUrl(response.getHeaders().getLocation().getPath());
    }

    private long getIdFromUrl(String url) {
        String[] splited = url.split("/");
        String idString = splited[splited.length - 1];
        return Long.parseLong(idString);
    }

    public Optional<SensorInfo> getNeighbour(long id) {
        final ResponseEntity<SensorInfo> response =
            restTemplate.getForEntity("http://localhost:8080/sensor/" + id + "/neighbour", SensorInfo.class);

        if (Objects.equals(response.getStatusCode(), HttpStatus.NO_CONTENT)) {
            return Optional.empty();
        }

        return Optional.ofNullable(response.getBody());
    }

    public void saveSensorData(SensorReadingDto sensorReadingDto, long id) {
        restTemplate.postForEntity("http://localhost:8080/sensor/" + id, sensorReadingDto, Void.class);
    }

}
