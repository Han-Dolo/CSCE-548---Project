package com.campus.rsvp.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RsvpApiApplication {
    public static void main(String[] args) {
        // Hosting (local platform):
        // 1) Ensure MySQL is running and schema/seed scripts are loaded.
        // 2) Run: mvn -q -DskipTests package
        // 3) Run: mvn -q spring-boot:run -Dspring-boot.run.mainClass="com.campus.rsvp.api.RsvpApiApplication"
        // 4) Service will be hosted at http://localhost:8080
        // If you want to access from another device on the same network, open port 8080
        // and use http://<your-ip>:8080 (example: http://192.168.x.x:8080).
        SpringApplication.run(RsvpApiApplication.class, args);
    }
}
