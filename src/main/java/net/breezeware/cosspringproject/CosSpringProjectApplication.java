package net.breezeware.cosspringproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main entry point for the COS (Cafeteria Ordering System) Spring Boot
 * application.
 * This class configures and starts the Spring Boot application.
 */
@SpringBootApplication
public class CosSpringProjectApplication {

    /**
     * The main method that starts the Spring Boot application.
     * @param args Command line arguments (if any).
     */
    public static void main(String[] args) {
        SpringApplication.run(CosSpringProjectApplication.class, args);
    }
}
