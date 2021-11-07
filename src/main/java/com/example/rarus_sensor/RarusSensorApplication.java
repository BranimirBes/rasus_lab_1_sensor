package com.example.rarus_sensor;

import com.example.rarus_sensor.grpc.RasusSensorReadingServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;

@EnableScheduling
@SpringBootApplication
public class RarusSensorApplication implements CommandLineRunner {

    private final RasusSensorReadingServer server;

    @Autowired
    public RarusSensorApplication(RasusSensorReadingServer server) {
        this.server = server;
    }

    public static void main(String[] args) {
        SpringApplication.run(RarusSensorApplication.class, args);
    }

    @Override
    public void run(String[] args) throws IOException, InterruptedException {
        server.start();
        server.blockUntilShutdown();
    }

}
