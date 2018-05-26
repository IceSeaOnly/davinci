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
public class ServiceLookController extends BaseController implements InitializingBean {
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
    public Object serviceList(Integer page, String appName, String status, String method) {
        JSONObject data = newJSONObject();
        long sum = davinciServicesService.count();
        data.put("total", sum);
        data.put("page", page);
        data.put("pageSize", 10);
        data.put("totalPage", sum / 10 + 1);
        data.put("list", davinciServicesService.list(page > 1 ? page - 1 : 0, appName, status, method));
        return success(data, null);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        davinciServicesService.setAllOffline();
    }
}
