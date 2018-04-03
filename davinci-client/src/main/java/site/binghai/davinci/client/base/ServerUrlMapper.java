package site.binghai.davinci.client.base;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import site.binghai.davinci.common.enums.UrlEnum;
import site.binghai.davinci.common.utils.HttpUtils;

/**
 * Created by IceSea on 2018/4/1.
 * GitHub: https://github.com/IceSeaOnly
 * 相关服务器地址映射工具
 */
//@Component
public class ServerUrlMapper implements InitializingBean {
    private static JSONObject map;
    @Autowired
    private ConfigAdapter configAdapter;

    public String getUrl(UrlEnum serverUrlEnum) {
        String url = map.getString(serverUrlEnum.getKey());
        return configAdapter.getServerIp() + url;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String cfgs = HttpUtils.sendGet(configAdapter.getServerIp() + "/" + UrlEnum.SERVER_CONFIG_CENTER.getKey(), null);
        if (StringUtils.isBlank(cfgs)) {
            throw new Exception("get url mapping config error!");
        }

        map = JSONObject.parseObject(cfgs);
        if (map == null) {
            throw new Exception("decode url mapping error!");
        }
    }
}
