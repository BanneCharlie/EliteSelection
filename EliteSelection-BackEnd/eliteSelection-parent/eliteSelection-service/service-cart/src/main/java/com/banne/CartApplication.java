package com.banne;

import org.example.annonation.EnableUserTokenFeignInterceptor;
import org.example.annonation.EnableUserWebMvcConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class) // 排除数据库的自动化配置，Cart微服务不需要访问Mysql数据库
@EnableFeignClients(basePackages = {
        "org.example.feign.product"
})
@EnableUserWebMvcConfiguration
@EnableUserTokenFeignInterceptor
public class CartApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(CartApplication.class,args);
    }
}
