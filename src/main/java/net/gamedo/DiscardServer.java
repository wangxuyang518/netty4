package net.gamedo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.net.InetSocketAddress;


/**
 * Created by Administrator on 2017/1/9 0009.
 * Channel是Netty最核心的接口，一个Channel就是一个联络Socket的通道，通过Channel，你可以对Socket进行各种操作。
 *
 * ChannelHandler
 用Netty编写网络程序的时候，你很少直接操纵Channel，而是通过ChannelHandler来间接操纵Channel。

 ChannelPipeline实际上应该叫做ChannelHandlerPipeline，
 可以把ChannelPipeline看成是一个ChandlerHandler的链表，
 当需要对Channel进行某种处理的时候，Pipeline负责依次调用每一个Handler进行处理。
 每个Channel都有一个属于自己的Pipeline，调用Channel#pipeline()方法可以获得Channel的Pipeline，
 调用Pipeline#channel()方法可以获得Pipeline的Channel。

 （1）创建用于两个eventloopgroup对象，一个用于管理serversocketchannel，一个用于管理accept到的channel
 （2）创建serverbootstrap对象，
 （3）设置eventloopgroup
 （4）创建用于构建用到的channel的工厂类
 （5）设置childhandler，它的主要功能主要是用户定义代码来初始化accept到的channel
 （6）创建serversocketchannel，并对它进行初始化，绑定端口，以及register，并为serversocketchannel的pipeline设置默认的handler
 */
public class DiscardServer {
    private int port;
    private ChannelFuture channelFuture;
    public DiscardServer(int port){
        this.port=port;
    }
    public void run(){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // NIO服务器端的辅助启动类 降低服务器开发难度
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)// 类似NIO中serverSocketChannel
                    .option(ChannelOption.SO_BACKLOG, 1024)// 配置TCP参数
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                             socketChannel.pipeline().addLast(new ObjectEncoder(),
                                     new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)),
                                     new DiscardServerHandler());
                        }
                    });// 最后绑定I/O事件的处理类
            // 处理网络IO事件
            // 服务器启动后 绑定监听端口 同步等待成功 主要用于异步操作的通知回调 回调处理用的ChildChannelHandler
            ChannelFuture f = null;
            try {
                System.out.println("timeServer启动");
                f = serverBootstrap.bind(new InetSocketAddress(this.port)).sync();
                f.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 等待服务端监听端口关闭
        } finally {
            // 优雅退出 释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            System.out.println("服务器优雅的释放了线程资源...");
        }

    }

    public static void main(String[] args){
        new DiscardServer(8889).run();
    }
}
