package site.binghai.davinci.common.sockets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.Data;
import site.binghai.davinci.common.utils.SocketDataBundleTools;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by IceSea on 2018/4/2.
 * GitHub: https://github.com/IceSeaOnly
 */
@Data
public abstract class Server2ClientHandler extends ChannelInboundHandlerAdapter {
    private ChannelHandlerContext channelHandlerContext;
    private boolean actived = false;
    private Boolean targetIsDavinciClient;
    private BlockingQueue<Object> blockingQueue;

    private String remoteHost;
    private Integer remotePort;
    private String remoteAppName;


    public boolean isActived() {
        return actived;
    }

    /**
     * channelAction
     * channel 通道 action 活跃的
     * 当客户端主动链接服务端的链接后，这个通道就是活跃的了。也就是客户端与服务端建立了通信通道并且可以传输数据
     */
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().localAddress().toString() + " server channel active!");
        channelHandlerContext = ctx;
        actived = true;
        targetIsDavinciClient = null;
        blockingQueue = new LinkedBlockingQueue<>();
        workerStart();
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
     * channelInactive
     * channel 通道 Inactive 不活跃的
     * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端的关闭了通信通道并且不可以传输数据
     */
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().localAddress().toString() + " channel closed");
        actived = false;
        ctx.disconnect();
        ctx.close();
        whenChannelClosed();
    }

    public void post(Object data) {
        blockingQueue.add(data);
    }


    private String getMessage(ByteBuf buf) {
        byte[] con = new byte[buf.readableBytes()];
        buf.readBytes(con);
        try {
            return new String(con, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 功能：读取服务器发送过来的信息
     */

    private String last = null;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 第一种：接收字符串时的处理
        ByteBuf buf = (ByteBuf) msg;
        String message = getMessage(buf);
        if (last != null) {
            message = last + message;
            last = null;
        }
        while (message.contains("}{")) {
            int idx = message.indexOf("}{");
            clientMessageCome(message.substring(0, idx + 1));
            message = message.substring(idx + 1, message.length());
        }
        if (!SocketDataBundleTools.isRightJson(message)) {
            last = message;
        } else {
            clientMessageCome(message);
        }
    }


    /**
     * 功能：读取完毕客户端发送过来的数据之后的操作
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 第一种方法：写一个空的buf，并刷新写出区域。完成后关闭sock channel连接。
//        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
//        System.out.println("DONE!");
//         ctx.flush();
        // ctx.flush(); //
        // 第二种方法：在client端关闭channel连接，这样的话，会触发两次channelReadComplete方法。
        // ctx.flush().close().sync(); // 第三种：改成这种写法也可以，但是这中写法，没有第一种方法的好。
    }

    /**
     * 功能：服务端发生异常的操作
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        actived = false;
        ctx.close();
        whenExceptionCloseContext(cause);
    }


    protected abstract void whenExceptionCloseContext(Throwable cause);

    protected abstract void clientMessageCome(String rev);

    protected abstract void whenChannelClosed();
}