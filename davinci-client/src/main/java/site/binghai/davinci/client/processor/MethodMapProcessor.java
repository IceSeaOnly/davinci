package site.binghai.davinci.client.processor;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.binghai.davinci.client.reflect.MethodsMapper;
import site.binghai.davinci.common.def.HostConfig;
import site.binghai.davinci.common.enums.DataPackageEnum;

import java.util.*;

/**
 * Created by IceSea on 2018/5/22.
 * GitHub: https://github.com/IceSeaOnly
 */
@Service
public class MethodMapProcessor extends BaseProcessor {
    @Autowired
    private MethodsMapper methodsMapper;

    @Override
    public DataPackageEnum getAcceteType() {
        return DataPackageEnum.SERVICE_MAP_DATA;
    }

    @Override
    public void putData(Object data) {
        logger.info("updating remote services map...");

        Map<String, List<HostConfig>> maps = new HashMap<>();
        JSONObject obj = JSONObject.parseObject(data.toString());
        obj.keySet().forEach(v -> maps.put(v, obj.getJSONArray(v).toJavaList(HostConfig.class)));
        methodsMapper.refreshServiceMap(maps);
    }

    @Override
    protected void onBeanReady() {

    }
}
