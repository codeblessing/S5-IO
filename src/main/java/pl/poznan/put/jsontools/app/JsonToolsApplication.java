package pl.poznan.put.jsontools.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"pl.poznan.put.jsontools"})
public class JsonToolsApplication {
    public static void main(String[] args) {
        SpringApplication.run(JsonToolsApplication.class, args);
    }
}
