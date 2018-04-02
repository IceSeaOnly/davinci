package site.binghai.davinci.common.enums;

/**
 * Created by IceSea on 2018/4/1.
 * GitHub: https://github.com/IceSeaOnly
 */
public enum UrlEnum {
    SERVER_CONFIG_CENTER("配置中心","SERVER_CONFIG_CENTER"),
    MONITOR_SERVER_IP("监控中心IP","MONITOR_SERVER_IP"),
    ;

    private String name;
    private String key;

    UrlEnum(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }
}
