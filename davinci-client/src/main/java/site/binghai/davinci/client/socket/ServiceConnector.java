package site.binghai.davinci.client.socket;


import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandler;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.binghai.davinci.client.reflect.Call;
import site.binghai.davinci.client.reflect.MethodsMapper;
import site.binghai.davinci.common.def.HostConfig;
import site.binghai.davinci.common.sockets.Client;
import site.binghai.davinci.common.sockets.Client2ServerHandler;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by IceSea on 2018/5/18.
 * GitHub: https://github.com/IceSeaOnly
 * 每个远程类创建一个通讯代理对象，远程过程在这里进行
 */
@Service
@Log4j
public class ServiceConnector implements InitializingBean {
    @Autowired
    private MethodsMapper methodsMapper;
    private ExecutorService executor;
    private Map<String, Call> futurePool;
    private static ConcurrentHashMap<HostConfig, Client> clients;
    private static ServiceConnector serviceConnector; // 自我引用

    /**
     * 发起远程调用
     * */
    public Future<Call> post(Call call) throws Exception {
        Client client = getClient(call);
        RemoteCallTask task = new RemoteCallTask(client, futurePool, call);
        return executor.submit(task);
    }

    /**
     * 获取一个有效服务连接
     * */
    private Client getClient(Call call) throws Exception {
        HostConfig config = methodsMapper.getHostConfig(call.getFullMethodName());
        Client client = clients.get(config);
        if (client == null) {
            client = buildClient(config);
        }
        return client;
    }

    /**
     * 同步方式创建新的服务连接
     * */
    private synchronized Client buildClient(HostConfig config) throws InterruptedException {
        if (clients.get(config) != null) return clients.get(config);
        BlockingQueue<Boolean> waiter = new LinkedBlockingQueue<>();
        Client client = new Client() {
            @Override
            protected void onConnected() {
                waiter.add(Boolean.TRUE);
            }

            @Override
            protected String getHost() {
                return config.getIp();
            }

            @Override
            protected int getPort() {
                return config.getPort();
            }

            @Override
            public ChannelHandler clientHandler() {
                return new Client2ServerHandler(true) {
                    @Override
                    protected void whenExceptionCloseChannel(Throwable cause) {
                        log.error("channel closed caused by ", cause);
                    }

                    @Override
                    protected void serverMessageCome(String s) {
                        Call call = JSONObject.parseObject(s, Call.class);
                        futurePool.put(call.getToken(), call);
                    }
                };
            }
        };
        waiter.take();
        clients.put(config, client);
        return client;
    }

    /**
     * 获取单一工厂实例引用
     * */
    public static ServiceConnector getInstance() {
        return serviceConnector;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        executor = Executors.newCachedThreadPool();
        futurePool = new ConcurrentHashMap<>();
        clients = new ConcurrentHashMap<>();
        serviceConnector = this;
    }

}


