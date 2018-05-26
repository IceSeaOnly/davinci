package site.binghai.davinci.client.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.binghai.davinci.client.socket.MQConnector;
import site.binghai.davinci.common.enums.DataPackageEnum;

/**
 * Created by IceSea on 2018/5/26.
 * GitHub: https://github.com/IceSeaOnly
 * 种子机上线监听
 */
@Service
public class SeedJoinProcessor extends BaseProcessor {
    @Autowired
    private MQConnector mqConnector;

    @Override
    public DataPackageEnum getAcceteType() {
        return DataPackageEnum.DAVINCI_SERVER;
    }

    @Override
    public void putData(Object data) {
        logger.info("new seed server join,republish services...");
        mqConnector.rePublishLocalService();
    }

    @Override
    protected void onBeanReady() {

    }
}
