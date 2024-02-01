package org.example;

import org.apache.ibatis.annotations.Mapper;
import org.example.log.annotation.EnableLogAspect;
import org.example.manager.properties.MinioProperties;
import org.example.manager.properties.NoAuthUrls;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication()
@EnableLogAspect
@EnableAsync
@EnableConfigurationProperties(value = {NoAuthUrls.class, MinioProperties.class})
@MapperScan(basePackages = "org.example.manager.mapper",annotationClass = Mapper.class)
public class ManagerApplication {
    public static void main( String[] args ){
        SpringApplication.run( ManagerApplication.class, args );
    }
}
