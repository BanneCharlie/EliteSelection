package org.example.manager.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "elite.auth")
@Data
public class NoAuthUrls {
    private List<String>  noAuthurls;
}
