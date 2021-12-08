package pl.poznan.put.jsontools.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/// Comment
@SpringBootApplication(scanBasePackages = {"pl.poznan.put.jsontools.rest"})
public class TextTransformerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TextTransformerApplication.class, args);
    }
}
