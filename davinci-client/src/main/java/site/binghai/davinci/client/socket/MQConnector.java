package site.binghai.davinci.client.socket;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandler;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import site.binghai.davinci.client.configs.ConfigAdapter;
import site.binghai.davinci.client.processor.BaseProcessor;
import site.binghai.davinci.common.def.DataBundle;
import site.binghai.davinci.common.enums.DataPackageEnum;
import site.binghai.davinci.common.sockets.Client;
import site.binghai.davinci.common.sockets.Client2ServerHandler;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by IceSea on 2018/4/3.
 * GitHub: https://github.com/IceSeaOnly
 * 连接到MQ，以获取全局通知消息
 */
@Service
@Log4j
public class MQConnector extends Client implements InitializingBean, ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private ConfigAdapter configAdapter;
    @Autowired
    private LocalServiceProvider localServiceProvider;

    private Client2ServerHandler client2ServerHandler;

    private ConcurrentHashMap<DataPackageEnum,BaseProcessor> processors;

    /**
     * 连接“交管机”后，广播本地的服务
     */
    @Override
    protected void onConnected() {
        localServiceProvider.postService(client2ServerHandler);
    }

    @Override
    protected String getHost() {
        return configAdapter.getMqServerIp();
    }

    @Override
    protected int getPort() {
        return configAdapter.getMqServerPort();
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
        processors = new ConcurrentHashMap<>();
        client2ServerHandler = new Client2ServerHandler(true) {
            @Override
            protected void whenExceptionCloseChannel(Throwable cause) {
                log.error("Connect2MQ error!", cause);
            }

            @Override
            protected void serverMessageCome(String s) {
                DataBundle dataBundle = JSONObject.parseObject(s, DataBundle.class);
                BaseProcessor processor = processors.get(dataBundle.getType());
                if(processor != null){
                    processor.putData(dataBundle.getData());
                }else{
                    log.error("no processor for date type:"+dataBundle.getType());
                }
            }
        };
    }
}
