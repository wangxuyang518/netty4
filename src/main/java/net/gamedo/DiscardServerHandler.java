package net.gamedo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by Administrator on 2017/1/9 0009.
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {


    //数据接受完毕之后被执行
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf)msg;
        byte [] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String message = new String(req,"UTF-8");
        System.out.println("Netty-Server:Receive Message,"+ message);
        super.channelRead(ctx, msg);
}

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        super.exceptionCaught(ctx, cause);
    }
}
