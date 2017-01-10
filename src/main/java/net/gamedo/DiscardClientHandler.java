package net.gamedo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by Administrator on 2017/1/9 0009.
 */
public class DiscardClientHandler extends ChannelInboundHandlerAdapter {
    private ByteBuf firstMessage;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        byte[] req ="hello".getBytes();
        firstMessage= Unpooled.buffer(req.length);
        firstMessage.writeBytes(req);
        ctx.writeAndFlush(firstMessage);
        System.out.println("客户端active");
    }
}
