package site.binghai.davinci;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import site.binghai.davinci.common.def.HostConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by IceSea on 2018/4/3.
 * GitHub: https://github.com/IceSeaOnly
 */
@SpringBootApplication
@ComponentScan("site.binghai.davinci.*")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
//        test();
    }

    public static  void test(){
        HostConfig hostConfig = new HostConfig();
        hostConfig.setPort(9999);
        hostConfig.setIp("127.0.0.1");
        hostConfig.setAppName("TEST");

        HostConfig that = new HostConfig();
        that.setPort(9999);
        that.setIp("127.0.0.1");
        that.setAppName("TEST");

        Map<HostConfig,HostConfig> map = new ConcurrentHashMap<>();
        map.put(hostConfig,hostConfig);

        System.out.println(map.get(that) == null);
    }
}
