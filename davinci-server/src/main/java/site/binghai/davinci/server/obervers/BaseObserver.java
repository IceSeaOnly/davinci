package site.binghai.davinci.server.obervers;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import site.binghai.davinci.common.enums.DataPackageEnum;
import site.binghai.davinci.common.utils.BaseBean;
import site.binghai.davinci.server.socket.MQConnector;

/**
 * Created by IceSea on 2018/5/21.
 * GitHub: https://github.com/IceSeaOnly
 */
public abstract class BaseObserver extends BaseBean implements InitializingBean {
    public abstract DataPackageEnum getAcceteType();

    public abstract void putData(Object data);
    protected abstract void onBeanReady();

    @Autowired
    private MQConnector MQConnector;

    @Override
    public final void afterPropertiesSet() throws Exception {
        MQConnector.setObserver(getAcceteType(), this);
        onBeanReady();
    }

}
