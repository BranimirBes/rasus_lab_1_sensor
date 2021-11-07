package com.example.rarus_sensor.grpc;

import com.example.rarus_sensor.SensorReadingRequest;
import com.example.rarus_sensor.SensorReadingResponse;
import com.example.rarus_sensor.SensorReadingServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class SensorReadingService extends SensorReadingServiceGrpc.SensorReadingServiceImplBase {
  private static final Logger logger = Logger.getLogger(SensorReadingService.class.getName());

  @Override
  public void requestSensorReading(
      SensorReadingRequest request, StreamObserver<SensorReadingResponse> responseObserver
  ) {

    // Create response
    SensorReadingResponse response = SensorReadingResponse.newBuilder()
        .setHumidity((float)12.5)
        .setCo((float)12.5)
        .setNo2((float)12.5)
        .setPressure((float)12.5)
        .setSo2((float)12.5)
        .setTemperature((float)12.5)
        .build();

    // Send response
    responseObserver.onNext(
        response
    );

    logger.info("Responding with: " + response.getCo());
    responseObserver.onCompleted();
  }
}
