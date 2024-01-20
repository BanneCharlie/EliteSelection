package org.example.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfig {
    @Bean
    public GroupedOpenApi adminApi() {      // 创建了一个api接口的分组
        return GroupedOpenApi.builder()
                .group("admin-api")         // 分组名称  admin-api
                .pathsToMatch("/admin/**")  // 接口请求路径规则  满足当前设定规则的接口全部在 admin-api组中
                .build();
    }

    /***
     * @description 自定义 描述接口信息
     */
    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("尚品甑选API接口文档")
                        .version("1.0")
                        .description("尚品甑选API接口文档")
                        .contact(new Contact().name("banne"))); // 设定作者
    }

}
