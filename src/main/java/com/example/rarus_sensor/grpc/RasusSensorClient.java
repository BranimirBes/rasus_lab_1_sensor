package com.example.rarus_sensor.grpc;

import com.example.rarus_sensor.SensorReadingRequest;
import com.example.rarus_sensor.SensorReadingResponse;
import com.example.rarus_sensor.SensorReadingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * The type Simple unary rpc client.
 */
public class RasusSensorClient {

  private static final Logger logger = Logger.getLogger(RasusSensorClient.class.getName());


  private final ManagedChannel channel;
  private final SensorReadingServiceGrpc.SensorReadingServiceBlockingStub uppercaseBlockingStub;

  /**
   * Instantiates a new Simple unary rpc client.
   *
   * @param host the host
   * @param port the port
   */
  public RasusSensorClient(String host, int port) {
    this.channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();

    uppercaseBlockingStub = SensorReadingServiceGrpc.newBlockingStub(channel);

  }

  /**
   * Stop the client.
   *
   * @throws InterruptedException the interrupted exception
   */
  public void stop() throws InterruptedException {
//    Initiates an orderly shutdown in which preexisting calls continue but new calls are
//    immediately cancelled. Waits for the channel to become terminated, giving up if the timeout
//    is reached.
    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }


  public SensorReadingResponse requestSensorReading() {
    SensorReadingRequest request = SensorReadingRequest.newBuilder().build();

    logger.info("Sending request");
    SensorReadingResponse response = uppercaseBlockingStub.requestSensorReading(request);
    logger.info("Recived " + response.getCo());

    return response;
  }
}
