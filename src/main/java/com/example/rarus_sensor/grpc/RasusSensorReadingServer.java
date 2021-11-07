package com.example.rarus_sensor.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Component
public class RasusSensorReadingServer {

    private static final Logger logger = Logger.getLogger(RasusSensorReadingServer.class.getName());

    private Server server;
    private final SensorReadingService service;

    @Value("${server.port}")
    private int port;

    @Autowired
    public RasusSensorReadingServer(SensorReadingService service) {
        this.service = service;
    }

    public void start() throws IOException {
        // Register the service
        server = ServerBuilder.forPort(port)
            .addService(service)
            .build()
            .start();
        logger.info("Server started on " + port);

        //  Clean shutdown of server in case of JVM shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("Shutting down gRPC server since JVM is shutting down");
            try {
                RasusSensorReadingServer.this.stop();
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
            System.err.println("Server shut down");
        }));
    }

    public void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

}
