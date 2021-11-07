package com.example.rarus_sensor;

import com.example.rarus_sensor.dto.SensorInfo;
import com.example.rarus_sensor.exception.RegistrationFailedException;
import com.example.rarus_sensor.grpc.RasusSensorReadingServer;
import com.example.rarus_sensor.service.MySensorInfo;
import com.example.rarus_sensor.service.NeighbourSensor;
import com.example.rarus_sensor.service.SensorRestService;
import com.example.rarus_sensor.util.SensorInfoGeneratingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

@EnableScheduling
@SpringBootApplication
public class RarusSensorApplication implements CommandLineRunner {

    private final RasusSensorReadingServer server;
    private final SensorRestService sensorRestService;
    private final SensorInfoGeneratingUtil sensorInfoGeneratingUtil;
    private final NeighbourSensor neighbourSensor;
    private final MySensorInfo mySensorInfo;
    private static final Logger logger = Logger.getLogger(RarusSensorApplication.class.getName());

    @Autowired
    public RarusSensorApplication(RasusSensorReadingServer server,
        SensorRestService sensorRestService,
        SensorInfoGeneratingUtil sensorInfoGeneratingUtil,
        NeighbourSensor neighbourSensor, MySensorInfo mySensorInfo) {
        this.server = server;
        this.sensorRestService = sensorRestService;
        this.sensorInfoGeneratingUtil = sensorInfoGeneratingUtil;
        this.neighbourSensor = neighbourSensor;
        this.mySensorInfo = mySensorInfo;
    }

    public static void main(String[] args) {
        SpringApplication.run(RarusSensorApplication.class, args);
    }

    @Override
    public void run(String[] args) throws IOException, InterruptedException {
        long id;

        try {
            id = sensorRestService.registerSensor(sensorInfoGeneratingUtil.generateSensorInfo());
        } catch (RegistrationFailedException e) {
            logger.info("Registration failed");
            return;
        }

        mySensorInfo.setId(id);
        Optional<SensorInfo> sensorInfo = sensorRestService.getNeighbour(id);
        neighbourSensor.updateInformation(sensorInfo);

        server.start();
        server.blockUntilShutdown();
    }

}
