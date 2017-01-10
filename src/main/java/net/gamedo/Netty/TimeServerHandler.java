package net.gamedo.Netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * Author: zxc12788.
 * Date: 2015/8/28.
 * Description:
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        System.out.println("服务器端发生问题 退出"+cause.getMessage());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务器开始读取客户端消息");
        ByteBuf buf= (ByteBuf) msg;//封装ByteBuffer
        byte[] req=new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body=new String(req,"utf-8");
        System.out.println("the time server receive order:"+body);
        String curentTime="QUERY TIME ORDER".equalsIgnoreCase(body)?new Date(System.currentTimeMillis()).toString():"BAD ORDER";
        ByteBuf resp= Unpooled.copiedBuffer(curentTime.getBytes());
        ctx.write(resp);
        System.out.println("服务器做出了响应");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
        System.out.println("服务器端消息完毕 响应完毕");
    }
}
