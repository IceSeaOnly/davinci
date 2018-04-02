package site.binghai.davinci;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by IceSea on 2018/3/31.
 * GitHub: https://github.com/IceSeaOnly
 */
@SpringBootApplication
@ComponentScan("site.binghai.produced.*")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
