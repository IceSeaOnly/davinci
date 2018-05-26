package site.binghai.davinci.server.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.binghai.davinci.server.service.DavinciServicesService;

/**
 * Created by IceSea on 2018/5/25.
 * GitHub: https://github.com/IceSeaOnly
 */
@RestController
@RequestMapping("/")
@CrossOrigin("*")
public class ServiceLookController extends BaseController implements InitializingBean{
    @Autowired
    private DavinciServicesService davinciServicesService;

    @RequestMapping("serviceStatus")
    public Object serviceStatus() {
        JSONArray array = newJSONArray();
        long online = davinciServicesService.findAll(9999).stream().filter(v -> v.getOnLine()).count();
        array.add(buildStatus("服务在线", online));
        array.add(buildStatus("服务总数", davinciServicesService.count()));
        array.add(buildStatus("种子机", 1));
        return success(array, null);
    }

    private JSONObject buildStatus(String title, long count) {
        JSONObject object = newJSONObject();
        object.put("title", title);
        object.put("count", count);
        return object;
    }

    @RequestMapping("serviceList")
    public Object serviceList() {
        JSONObject data = newJSONObject();
        data.put("list", davinciServicesService.findAll(9999));
        return success(data, null);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        davinciServicesService.setAllOffline();
    }
}
