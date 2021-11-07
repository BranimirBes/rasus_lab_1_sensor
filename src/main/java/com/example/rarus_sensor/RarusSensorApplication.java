package com.example.rarus_sensor;

import com.example.rarus_sensor.dto.SensorInfo;
import com.example.rarus_sensor.grpc.RasusSensorReadingServer;
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
    private static final Logger logger = Logger.getLogger(RarusSensorApplication.class.getName());

    @Autowired
    public RarusSensorApplication(RasusSensorReadingServer server,
        SensorRestService sensorRestService,
        SensorInfoGeneratingUtil sensorInfoGeneratingUtil,
        NeighbourSensor neighbourSensor) {
        this.server = server;
        this.sensorRestService = sensorRestService;
        this.sensorInfoGeneratingUtil = sensorInfoGeneratingUtil;
        this.neighbourSensor = neighbourSensor;
    }

    public static void main(String[] args) {
        SpringApplication.run(RarusSensorApplication.class, args);
    }

    @Override
    public void run(String[] args) throws IOException, InterruptedException {
        long id = sensorRestService.registerSensor(sensorInfoGeneratingUtil.generateSensorInfo());
        Optional<SensorInfo> sensorInfo = sensorRestService.getNeighbour(id);
        neighbourSensor.updateInformation(sensorInfo);
        logger.info(neighbourSensor.getHost());
        logger.info(neighbourSensor.getPort() + "");

        // server.start();
        // server.blockUntilShutdown();
    }

}
