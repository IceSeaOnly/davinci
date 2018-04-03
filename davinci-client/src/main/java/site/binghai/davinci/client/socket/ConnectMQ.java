package site.binghai.davinci.client.socket;

import io.netty.channel.ChannelHandler;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import site.binghai.davinci.common.sockets.Client;
import site.binghai.davinci.common.sockets.Client2ServerHandler;

/**
 * Created by IceSea on 2018/4/3.
 * GitHub: https://github.com/IceSeaOnly
 */
@Service
@Log4j
public class ConnectMQ extends Client implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        start();
    }

    @Override
    protected String getHost() {
        return "127.0.0.1";
    }

    @Override
    protected int getPort() {
        return 8848;
    }

    @Override
    protected ChannelHandler clientHandler() {
        return new Client2ServerHandler(true) {
            @Override
            protected void whenExceptionCloseChannel(Throwable cause) {
                log.error("Connect2MQ error!", cause);
            }

            @Override
            protected void serverMessageCome(String s) {
                log.info(s);
            }
        };
    }
}
