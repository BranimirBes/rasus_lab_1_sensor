package com.example.rarus_sensor;

import com.example.rarus_sensor.grpc.RasusSensorReadingServer;
import com.example.rarus_sensor.service.SensorRestService;
import com.example.rarus_sensor.util.SensorInfoGeneratingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.util.logging.Logger;

@EnableScheduling
@SpringBootApplication
public class RarusSensorApplication implements CommandLineRunner {

    private final RasusSensorReadingServer server;
    private final SensorRestService sensorRestService;
    private final SensorInfoGeneratingUtil sensorInfoGeneratingUtil;
    private static final Logger logger = Logger.getLogger(RarusSensorApplication.class.getName());

    @Autowired
    public RarusSensorApplication(RasusSensorReadingServer server,
        SensorRestService sensorRestService,
        SensorInfoGeneratingUtil sensorInfoGeneratingUtil) {
        this.server = server;
        this.sensorRestService = sensorRestService;
        this.sensorInfoGeneratingUtil = sensorInfoGeneratingUtil;
    }

    public static void main(String[] args) {
        SpringApplication.run(RarusSensorApplication.class, args);
    }

    @Override
    public void run(String[] args) throws IOException, InterruptedException {
        long id = sensorRestService.registerSensor(sensorInfoGeneratingUtil.generateSensorInfo());

        // server.start();
        // server.blockUntilShutdown();
    }

}
