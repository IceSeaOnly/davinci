package site.binghai.davinci.mq.socket;

import io.netty.channel.ChannelHandler;
import site.binghai.davinci.common.sockets.Server;

/**
 * Created by IceSea on 2018/4/2.
 * GitHub: https://github.com/IceSeaOnly
 */
public class ServerListener extends Server {
    private ClientManager clientManager;

    public ServerListener() {
        clientManager = new ClientManager();
        clientManager.start();
    }

    @Override
    protected int getPort() {
        return 8848;
    }

    @Override
    protected ChannelHandler getServerHandler() {
        return clientManager.newServer2ClientHandler();
    }
}
