package org.example;

import org.example.manager.properties.NoAuthUrls;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication()
@EnableConfigurationProperties(value = {NoAuthUrls.class})
public class ManagerApplication {
    public static void main( String[] args ){
        SpringApplication.run( ManagerApplication.class, args );
    }
}
