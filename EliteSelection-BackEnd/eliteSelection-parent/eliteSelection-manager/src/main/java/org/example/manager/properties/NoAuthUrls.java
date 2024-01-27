package org.example.manager.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties(prefix = "elite.auth")
@Component
@Data
public class NoAuthUrls {
    private List<String>  noAuthurls;
}
