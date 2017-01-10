package net.gamedo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by Administrator on 2017/1/9 0009.
 */
public class DiscardClient  {

    public static void main(String[] args) throws InterruptedException {
        String host = "127.0.0.1";
        int port = 8889;
          EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch)
                                throws Exception {
                            ch.pipeline().addLast(new DiscardClientHandler());
                        }
                    });
            //异步链接服务器 同步等待链接成功
            ChannelFuture f = b.connect(new InetSocketAddress(
                    host,port)).sync();
            //等待链接关闭
            f.channel().closeFuture().sync();
        }
        finally {
            group.shutdownGracefully();
        }
    }
}

