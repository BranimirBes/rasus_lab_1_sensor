package com.example.rarus_sensor.service;

import com.example.rarus_sensor.dto.SensorInfo;
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

@Service
public class SensorRestService {
    private final RestTemplate restTemplate;

    @Autowired
    public SensorRestService(RestTemplateBuilder builder) {
        this.restTemplate = builder
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }

    public long registerSensor(SensorInfo sensorInfo) {
        final ResponseEntity<Void> response = restTemplate.postForEntity("http://localhost:8080/sensor/", sensorInfo, Void.class);

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

}
