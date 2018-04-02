package site.binghai.davinci;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by IceSea on 2018/4/2.
 * GitHub: https://github.com/IceSeaOnly
 */
@SpringBootApplication
@ComponentScan("site.binghai.davinci.*")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
