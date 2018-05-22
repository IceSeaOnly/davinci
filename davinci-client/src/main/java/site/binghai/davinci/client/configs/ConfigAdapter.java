package site.binghai.davinci.client.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import site.binghai.davinci.common.def.HostConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by IceSea on 2018/4/1.
 * GitHub: https://github.com/IceSeaOnly
 * 客户端配置注入器
 */
@Component
@Data
@ConfigurationProperties(prefix = "davinci")
@PropertySource("classpath:davinci.properties")
public class ConfigAdapter {
    private String thisAppPortIp;
    private int thisAppPortPort;
    private String appName;
    private String mqServerIp;
    private int mqServerPort;


    public HostConfig getThisAppHostConfig() {
        HostConfig hostConfig = new HostConfig();
        hostConfig.setAppName(appName);
        hostConfig.setIp(thisAppPortIp);
        hostConfig.setPort(thisAppPortPort);
        return hostConfig;
    }
}
