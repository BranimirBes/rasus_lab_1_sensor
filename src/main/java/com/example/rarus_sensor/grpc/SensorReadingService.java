package com.example.rarus_sensor.grpc;

import com.example.rarus_sensor.SensorReadingRequest;
import com.example.rarus_sensor.SensorReadingResponse;
import com.example.rarus_sensor.SensorReadingServiceGrpc;
import com.example.rarus_sensor.service.SensorReadingFileService;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class SensorReadingService extends SensorReadingServiceGrpc.SensorReadingServiceImplBase {

    private static final Logger logger = Logger.getLogger(SensorReadingService.class.getName());

    private final SensorReadingFileService sensorReadingFileService;

    @Autowired
    public SensorReadingService(SensorReadingFileService sensorReadingFileService) {
        this.sensorReadingFileService = sensorReadingFileService;
    }

    @Override
    public void requestSensorReading(
        SensorReadingRequest request, StreamObserver<SensorReadingResponse> responseObserver
    ) {

        // Create response
        SensorReadingResponse response = sensorReadingFileService.getSensorReading();

        // Send response
        responseObserver.onNext(
            response
        );

        logger.info("Responding with: " + response.getCo());
        responseObserver.onCompleted();
    }

}
