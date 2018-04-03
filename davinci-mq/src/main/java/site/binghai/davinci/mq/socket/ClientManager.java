package site.binghai.davinci.mq.socket;

import io.netty.channel.ChannelHandler;
import site.binghai.davinci.common.def.DataBundle;
import site.binghai.davinci.common.enums.DataPackageEnum;
import site.binghai.davinci.common.sockets.Server2ClientHandler;
import site.binghai.davinci.common.utils.SocketDataBundleTools;
import site.binghai.davinci.common.utils.TimeTools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by IceSea on 2018/4/2.
 * GitHub: https://github.com/IceSeaOnly
 */
public class ClientManager extends Thread {
    private static List<Server2ClientHandler> clients = new ArrayList<>();
    // MQ批量发送到客户端的数据队列
    private static ConcurrentLinkedQueue<DataBundle> toC = new ConcurrentLinkedQueue<>();
    // MQ批量发送到服务端的数据队列
    private static ConcurrentLinkedQueue<DataBundle> toS = new ConcurrentLinkedQueue<>();

    public ChannelHandler newServer2ClientHandler() {
        Server2ClientHandler handler = new Server2ClientHandler() {
            @Override
            protected void whenExceptionCloseContext(Throwable cause) {
                cause.printStackTrace();
            }

            @Override
            protected void clientMessageCome(String rev) {
                HandleMessageFromClient(this, rev);
            }
        };

        clients.add(handler);
        return handler;
    }

    private void HandleMessageFromClient(Server2ClientHandler server2ClientHandler, String rev) {
        DataBundle dataBundle = SocketDataBundleTools.decodeAsDataBundle(rev);
        if (dataBundle == null) {
            return;
        }

        switch (dataBundle.getType()) {
            case DAVINCI_CLIENT:
                System.out.println("A new Davinci Client Connected.");
                server2ClientHandler.setTargetIsDavinciClient(Boolean.TRUE);
                break;
            case DAVINCI_SERVER:
                System.out.println("A new Davinci Server Connected.");
                server2ClientHandler.setTargetIsDavinciClient(Boolean.FALSE);
                break;
        }

        if (null == server2ClientHandler.getTargetIsDavinciClient()) {
            System.out.println("client can't be identified");
            return;
        }

        if (server2ClientHandler.getTargetIsDavinciClient()) {
            toS.add(dataBundle);
        } else {
            toC.add(dataBundle);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println(String.format("clients:%d, toC: %d, toS: %d", clients.size(), toC.size(), toS.size()));
                while (!toC.isEmpty() || !toS.isEmpty()) {
                    post(toC, true);
                    post(toS, false);
                }

                try {
                    sleep(1000);
                    heartBeats();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                System.err.println("Client Manager Error When Post Data!");
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送心跳包
     * 心跳间隔控制
     */
    private int preSecond = 5;

    private void heartBeats() {
        if (TimeTools.currentSecond() % preSecond != 0) return;
        DataBundle heartBeat =
                SocketDataBundleTools.encodeData("now is : " + TimeTools.currentTS(),
                        DataPackageEnum.HEART_BEATS);

        toC.add(heartBeat);
        toS.add(heartBeat);
    }

    private void post(ConcurrentLinkedQueue<DataBundle> queue, boolean toClients) {
        if (null == queue || queue.isEmpty()) {
            return;
        }

        DataBundle data = queue.poll();
        Iterator<Server2ClientHandler> it = clients.iterator();
        while (it.hasNext()) {
            Server2ClientHandler s = it.next();
            if (!s.isActived()) {
                it.remove();
            } else {
                if (s.getTargetIsDavinciClient() != null) {
                    if (!toClients ^ s.getTargetIsDavinciClient()) {
                        s.post(SocketDataBundleTools.toPostData(data));
                    }
                }
            }
        }
    }

}
