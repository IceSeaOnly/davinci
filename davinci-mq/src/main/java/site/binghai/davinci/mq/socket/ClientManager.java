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
    // 服务端批量发送到客户端的数据队列
    private static ConcurrentLinkedQueue<DataBundle> dataBundles = new ConcurrentLinkedQueue<>();

    public ChannelHandler newServer2ClientHandler() {
        Server2ClientHandler handler = new Server2ClientHandler() {
            @Override
            protected void whenExceptionCloseContext(Throwable cause) {
                cause.printStackTrace();
            }

            @Override
            protected void clientMessageCome(String rev) {
                System.out.println(rev);
            }
        };

        clients.add(handler);
        return handler;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println(String.format("clients:%d, queue: %d", clients.size(), dataBundles.size()));
                while (!dataBundles.isEmpty()) {
                    post(dataBundles.poll());
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
     */
    private void heartBeats() {
        DataBundle heartBeat =
                SocketDataBundleTools.encodeData("now is : " + TimeTools.currentTS(),
                        DataPackageEnum.HEART_BEATS);

        dataBundles.add(heartBeat);
    }

    private void post(DataBundle data) {
        Iterator<Server2ClientHandler> it = clients.iterator();
        while (it.hasNext()) {
            Server2ClientHandler s = it.next();
            if (!s.isActived()) {
                it.remove();
            } else {
                s.post(SocketDataBundleTools.toPostData(data));
            }
        }
    }

}
