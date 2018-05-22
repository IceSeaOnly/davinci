package site.binghai.davinci.client.reflect;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import site.binghai.davinci.common.def.HostConfig;
import site.binghai.davinci.common.utils.TimeTools;

import java.util.*;

/**
 * Created by IceSea on 2018/5/22.
 * GitHub: https://github.com/IceSeaOnly
 */
@Service
public class MethodsMapper implements InitializingBean {
    private Random random;

    /**
     * 各种方法接口的服务地址
     */
    private static Map<String, List<HostConfig>> serviceMapper = new HashMap<>();

    public void refreshServiceMap(Map<String, List<HostConfig>> maps) {
        maps.forEach((k, v) -> {
            if (!serviceMapper.containsKey(k)) {
                serviceMapper.put(k, v);
            } else {
                serviceMapper.get(k).addAll(v);
            }
        });
    }

    /**
     * todo 将来LB在这里处理
     */
    public HostConfig getHostConfig(String method) throws Exception {
        List<HostConfig> configs = serviceMapper.get(method);
        if (CollectionUtils.isEmpty(configs)) {
            throw new Exception("no provider for this method:" + method);
        }

        return configs.get(randNextInt(configs.size()));
    }


    private int randNextInt(int size) {
        return random.nextInt(size);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        random = new Random(TimeTools.currentTS());
    }
}
