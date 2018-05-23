package site.binghai.davinci.common.sockets;

/**
 * Created by IceSea on 2018/4/2.
 * GitHub: https://github.com/IceSeaOnly
 * 客户端向服务端的处理组件
 */

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import site.binghai.davinci.common.def.DataBundle;
import site.binghai.davinci.common.utils.SocketDataBundleTools;

import java.nio.charset.Charset;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class Client2ServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private ChannelHandlerContext channelHandlerContext;
    private boolean asDavinciWorker;
    private BlockingQueue<Object> blockingQueue;

    public Client2ServerHandler(boolean asDavinciWorker) {
        this.asDavinciWorker = asDavinciWorker;
    }

    /**
     * 向服务端发送数据
     * 激活后随即发送一个数据包表名自己是davinci工作机还是davinci种子机
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel to server opened: " + ctx.channel().localAddress() + " channelActive");
        channelHandlerContext = ctx;
        blockingQueue = new LinkedBlockingQueue<>();
        workerStart();
        post(asDavinciWorker ? SocketDataBundleTools.asClient() : SocketDataBundleTools.asServer());
    }

    private final void workerStart() {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Object data = blockingQueue.take();
                        channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer(data.toString(), CharsetUtil.UTF_8)); // 必须有flush
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    /**
     * 数据发送方法
     */
    public void post(Object data) {
        blockingQueue.add(data);
    }

    /**
     * channelInactive
     * channel 通道 Inactive 不活跃的
     * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端的关闭了通信通道并且不可以传输数据
     */
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel to server closed! " + ctx.channel().localAddress() + "channelInactive");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        ByteBuf buf = msg.readBytes(msg.readableBytes());
        String message = buf.toString(Charset.forName("utf-8"));
        while (message.contains("}{")) {
            int idx = message.indexOf("}{");
            serverMessageCome(message.substring(0, idx + 1));
            message = message.substring(idx + 1, message.length());
        }
        serverMessageCome(message);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        whenExceptionCloseChannel(cause);
    }

    protected abstract void whenExceptionCloseChannel(Throwable cause);

    protected abstract void serverMessageCome(String s);
}
