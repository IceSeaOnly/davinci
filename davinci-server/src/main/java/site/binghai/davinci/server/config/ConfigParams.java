package site.binghai.davinci.server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by IceSea on 2018/5/21.
 * GitHub: https://github.com/IceSeaOnly
 */
@Component
@Data
@ConfigurationProperties(prefix = "davinci")
@PropertySource("classpath:application.properties")
public class ConfigParams {
    private String mqServerIp;
    private int mqServerPort;
}
