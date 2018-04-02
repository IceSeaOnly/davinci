package site.binghai.davinci.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IceSea on 2018/4/1.
 * GitHub: https://github.com/IceSeaOnly
 */
@RestController
public class UrlMapperController {
    @RequestMapping("SERVER_CONFIG_CENTER")
    public Object SERVER_CONFIG_CENTER(){
        return "";
    }
}
