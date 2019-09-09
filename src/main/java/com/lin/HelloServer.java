package com.lin;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Netty 的使用例子
 * @author lkmc2
 * @date 2019/9/9 23:32
 */
public class HelloServer {

    public static void main(String[] args) throws InterruptedException {
        // 定义一对线程组
        // 主线程组，用于接受客户端的连接，但是不做任何处理，跟老板一样，不做事
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        // 从线程组，老板线程会把任务丢给他，让手下线程去做任务
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // Netty 服务器的创建，ServerBootstrap 是一个启动类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup) // 设置主线程组
                    .channel(NioServerSocketChannel.class) // 设置 Nio 的 Socket 双向通道
                    .childHandler(null); // 子处理器，用于处理 worker 线程的事件

            // 启动 server ，并设置 8088 为启动的端口号，同时启动方式为同步
            ChannelFuture channelFuture = serverBootstrap.bind(8088).sync();

            // 监听关闭的 channel ，设置为同步方式
            channelFuture.channel().closeFuture().sync();
        } finally {
            // 优雅的关闭主线程和从线程组
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
