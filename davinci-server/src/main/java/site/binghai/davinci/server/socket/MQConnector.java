package site.binghai.davinci.server.socket;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandler;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import site.binghai.davinci.common.def.DataBundle;
import site.binghai.davinci.common.enums.DataPackageEnum;
import site.binghai.davinci.common.sockets.Client;
import site.binghai.davinci.common.sockets.Client2ServerHandler;
import site.binghai.davinci.common.utils.SocketDataBundleTools;
import site.binghai.davinci.server.config.ConfigParams;
import site.binghai.davinci.server.obervers.BaseObserver;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IceSea on 2018/5/21.
 * GitHub: https://github.com/IceSeaOnly
 */
@Service
@Log4j
public class MQConnector extends Client implements InitializingBean, ApplicationListener<ContextRefreshedEvent> {
    private Client2ServerHandler client2ServerHandler;
    @Autowired
    private ConfigParams configParams;
    private Map<DataPackageEnum, BaseObserver> observers;

    public void setObserver(DataPackageEnum type, BaseObserver observer) {
        observers.put(type, observer);
    }

    @Override
    protected void onConnected() {
        log.info("MQ connected succeed.");
    }

    @Override
    protected String getHost() {
        return configParams.getMqServerIp();
    }

    @Override
    protected int getPort() {
        return configParams.getMqServerPort();
    }

    @Override
    public ChannelHandler clientHandler() {
        return client2ServerHandler;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() != null) return;
        new Thread(() -> {
            try {
                setup();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        observers = new HashMap<>();
        client2ServerHandler = new Client2ServerHandler(false) {
            @Override
            protected void whenExceptionCloseChannel(Throwable cause) {
                log.error("Connect2MQ error!", cause);
            }

            @Override
            protected void serverMessageCome(String s) {
                try {
                    handleMessage(s);
                } catch (Exception e) {
                    log.error("handle message error!message:" + s, e);
                }
            }
        };
    }

    private void handleMessage(String s) {
        DataBundle dataBundle = SocketDataBundleTools.decodeAsDataBundle(s);
        BaseObserver observer = observers.get(dataBundle.getType());
        if (observer != null) {
            observer.putData(dataBundle.getData());
        } else {
            log.error("no processor for this message :" + s);
        }
    }
}
