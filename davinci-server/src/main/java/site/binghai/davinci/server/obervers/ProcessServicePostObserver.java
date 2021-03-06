package site.binghai.davinci.server.obervers;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.binghai.davinci.common.def.HostConfig;
import site.binghai.davinci.common.enums.DataPackageEnum;
import site.binghai.davinci.server.service.MethodMapService;

import java.util.List;

/**
 * Created by IceSea on 2018/5/21.
 * GitHub: https://github.com/IceSeaOnly
 */
@Service
public class ProcessServicePostObserver extends BaseObserver {
    @Autowired
    private MethodMapService methodMapService;

    @Override
    public DataPackageEnum getAcceteType() {
        return DataPackageEnum.POST_LOCAL_SERVICES;
    }

    @Override
    public void putData(Object data) {
        JSONObject obj = (JSONObject) data;
        HostConfig hostConfig = obj.getObject("host", HostConfig.class);
        List<String> methods = obj.getJSONArray("methods").toJavaList(String.class);

        methodMapService.updateMethods(hostConfig, methods);
        logger.info("method updated:" + obj.toJSONString());
    }

    @Override
    protected void onBeanReady() {
    }
}
