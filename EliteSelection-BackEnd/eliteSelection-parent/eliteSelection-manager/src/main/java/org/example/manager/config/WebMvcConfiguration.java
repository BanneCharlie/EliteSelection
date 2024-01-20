package org.example.manager.config;

import org.example.manager.properties.NoAuthUrls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebMvcConfiguration implements WebMvcConfigurer {

    // 解决跨域问题
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 添加路径规则
                .allowedOriginPatterns("*")  // 允许请求来源的域规则
              .allowedMethods("*")
              .allowedHeaders("*")     // 允许所有请求头
              .allowCredentials(true)  // 是否允许跨域进行传递Cookie
              .maxAge(3600);
    }

    @Autowired
    private LoginAuthInterceptor loginAuthInterceptor;

    @Autowired
    private NoAuthUrls noAuthUrls;

    // 拦截器的注册
    @Override
    public void addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry registry) {
        registry.addInterceptor(loginAuthInterceptor)
//                .excludePathPatterns("/admin/system/index/login","/admin/system/index/generateValidateCode")
                .excludePathPatterns(noAuthUrls.getNoAuthurls())
                .addPathPatterns("/**");
    }
}
