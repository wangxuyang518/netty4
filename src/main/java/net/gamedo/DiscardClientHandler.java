package net.gamedo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by Administrator on 2017/1/9 0009.
 */
public class DiscardClientHandler extends ChannelInboundHandlerAdapter {
    private ByteBuf firstMessage;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        User user=new User();
        user.setUsername("wxy");
        user.setPassword("123456");
        ctx.writeAndFlush(user);
        System.out.println("客户端active");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        User user= (User) msg;
        System.out.println("服务端发送的数据:"+user.getUsername());
        System.out.println("服务端发送的数据:"+user.getUsername());
    }
}
