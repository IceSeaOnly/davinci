package site.binghai.davinci.client.processor;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import site.binghai.davinci.client.socket.MQConnector;
import site.binghai.davinci.common.enums.DataPackageEnum;
import site.binghai.davinci.common.utils.BaseBean;

/**
 * Created by IceSea on 2018/5/22.
 * GitHub: https://github.com/IceSeaOnly
 */
public abstract class BaseProcessor extends BaseBean implements InitializingBean {
    public abstract DataPackageEnum getAcceteType();
    public abstract void putData(Object data);
    protected abstract void onBeanReady();

    @Autowired
    private MQConnector mqConnector;

    @Override
    public final void afterPropertiesSet() throws Exception {
        mqConnector.regProcessor(this);
        onBeanReady();
    }
}
