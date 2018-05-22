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
import site.binghai.davinci.common.utils.SocketDataBundleTools;

import java.nio.charset.Charset;

public abstract class Client2ServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private ChannelHandlerContext channelHandlerContext;
    private boolean asDavinciWorker;

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
        post(asDavinciWorker ? SocketDataBundleTools.asClient() : SocketDataBundleTools.asServer());
    }

    /**
     * 数据发送方法
     */
    public void post(Object data) {
        channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer(data.toString(), CharsetUtil.UTF_8)); // 必须有flush
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
        serverMessageCome(buf.toString(Charset.forName("utf-8")));
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        whenExceptionCloseChannel(cause);
    }

    protected abstract void whenExceptionCloseChannel(Throwable cause);

    protected abstract void serverMessageCome(String s);
}
