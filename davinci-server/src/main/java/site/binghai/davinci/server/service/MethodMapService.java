package site.binghai.davinci.server.service;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.binghai.davinci.common.def.DataBundle;
import site.binghai.davinci.common.def.HostConfig;
import site.binghai.davinci.common.enums.DataPackageEnum;
import site.binghai.davinci.common.sockets.Client2ServerHandler;
import site.binghai.davinci.server.socket.MQConnector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by IceSea on 2018/4/2.
 * GitHub: https://github.com/IceSeaOnly
 * “服务发现”服务
 */
@Service
@Log4j
public class MethodMapService implements InitializingBean {
    private ConcurrentHashMap<String, List<HostConfig>> methodsMap;
    @Autowired
    private MQConnector mqConnector;

    public void updateMethods(HostConfig host, List<String> methods) {
        methods.forEach(v -> {
            List<HostConfig> ls = methodsMap.get(v);
            if (CollectionUtils.isEmpty(ls)) {
                ls = new ArrayList<>();
            }
            if (!ls.contains(host)) ls.add(host);
            methodsMap.put(v, ls);
        });

        mapChanged();
    }

    public void mapChanged() {
        DataBundle dataBundle = new DataBundle(methodsMap, DataPackageEnum.SERVICE_MAP_DATA);
        ((Client2ServerHandler) mqConnector.clientHandler()).post(JSONObject.toJSONString(dataBundle));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        methodsMap = new ConcurrentHashMap<>();
    }
}
