package com.example.rarus_sensor.grpc;

import com.example.rarus_sensor.SensorReadingRequest;
import com.example.rarus_sensor.SensorReadingResponse;
import com.example.rarus_sensor.SensorReadingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

public class RasusSensorClient {

    private final ManagedChannel channel;
    private final SensorReadingServiceGrpc.SensorReadingServiceBlockingStub uppercaseBlockingStub;

    public RasusSensorClient(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        uppercaseBlockingStub = SensorReadingServiceGrpc.newBlockingStub(channel);
    }

    public void stop() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public SensorReadingResponse requestSensorReading() {
        SensorReadingRequest request = SensorReadingRequest.newBuilder().build();
        return uppercaseBlockingStub.requestSensorReading(request);
    }

}
