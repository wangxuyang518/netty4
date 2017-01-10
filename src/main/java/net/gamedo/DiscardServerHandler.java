package net.gamedo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by Administrator on 2017/1/9 0009.
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {


    //数据接受完毕之后被执行
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        User user= (User) msg;
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        super.channelRead(ctx, msg);
}


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        User user=new User();
        user.setPassword("456");
        user.setUsername("wang");
        ctx.writeAndFlush(user);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        super.exceptionCaught(ctx, cause);
    }
}
