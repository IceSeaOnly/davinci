package site.binghai.davinci.client.socket;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandler;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.binghai.davinci.client.annotations.ContextListener;
import site.binghai.davinci.client.configs.ConfigAdapter;
import site.binghai.davinci.client.reflect.Call;
import site.binghai.davinci.common.def.DataBundle;
import site.binghai.davinci.common.enums.DataPackageEnum;
import site.binghai.davinci.common.sockets.Client2ServerHandler;
import site.binghai.davinci.common.sockets.Server;
import site.binghai.davinci.common.sockets.Server2ClientHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by IceSea on 2018/5/20.
 * GitHub: https://github.com/IceSeaOnly
 * 本地服务提供
 */
@Service
@Log4j
public class LocalServiceProvider extends Server implements InitializingBean {
    @Autowired
    private ConfigAdapter configAdapter;
    @Autowired
    private ContextListener contextListener;
    private ExecutorService executor;

    /**
     * 发布本地服务
     */
    public void postService(Client2ServerHandler client2ServerHandler) {
        JSONObject obj = new JSONObject();
        obj.put("host", configAdapter.getThisAppHostConfig());
        obj.put("methods", contextListener.getServices().keySet());
        DataBundle dataBundle = new DataBundle(obj, DataPackageEnum.POST_LOCAL_SERVICES);
        client2ServerHandler.post(JSONObject.toJSONString(dataBundle));
    }

    @Override
    protected int getPort() {
        return configAdapter.getThisAppPortPort();
    }

    @Override
    protected ChannelHandler getServerHandler() {
        return new Server2ClientHandler() {
            @Override
            protected void whenExceptionCloseContext(Throwable cause) {
                log.error("channel close caused by ", cause);
            }

            @Override
            protected void clientMessageCome(String rev) {
                Call call = JSONObject.parseObject(rev, Call.class);
                ResponseCallTask task = new ResponseCallTask(call, contextListener, this);
                executor.execute(task);
            }
        };
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        executor = Executors.newCachedThreadPool();
    }
}
