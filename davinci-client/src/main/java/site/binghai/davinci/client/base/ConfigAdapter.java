package site.binghai.davinci.client.base;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * Created by IceSea on 2018/4/1.
 * GitHub: https://github.com/IceSeaOnly
 * 客户端配置注入器
 */
@Component
@Data
public class ConfigAdapter {
    private String serverIp;
}
