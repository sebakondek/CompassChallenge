package org.compass;

import org.compass.service.MainService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final String INPUT_FILE = "src/main/resources/files/input/CompassSampleCode.csv";
    private static final String OUTPUT_FILE = "src/main/resources/files/output/CompassSampleCodeResults.csv";

    private final MainService mainService;

    public Application(MainService mainService) {
        this.mainService = mainService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        mainService.execute(INPUT_FILE, OUTPUT_FILE);
    }
}
