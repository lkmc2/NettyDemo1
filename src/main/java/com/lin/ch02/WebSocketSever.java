package com.lin.ch02;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * WebSocket 服务器
 * @author lkmc2
 * @date 2019/9/13 11:11
 */
public class WebSocketSever {

    public static void main(String[] args) throws InterruptedException {
        // 访问：http://localhost:9999

        // 定义一对线程组
        // 主线程组，用于接受客户端的连接，但是不做任何处理
        NioEventLoopGroup mainGroup = new NioEventLoopGroup();
        // 从线程组，用于处理主线程组传过来的任务
        NioEventLoopGroup subGroup = new NioEventLoopGroup();

        try {
            // Netty 服务器的创建，ServerBootstrap 是一个启动类
            ServerBootstrap server = new ServerBootstrap();
            server.group(mainGroup, subGroup)  // 设置线程组
                    .channel(NioServerSocketChannel.class) // 设置 Nio 的 Socket 双向通道
                    .childHandler(new WebSocketServerInitializer()); // 子处理器，用于处理 worker 线程的事件

            // 启动 server ，并设置 9999 为启动的端口号，同时启动方式为同步
            ChannelFuture future = server.bind(9999).sync();

            System.out.println("当前 Netty 服务器启动在：http://localhost:9999");

            // 监听关闭的 channel ，设置为同步方式
            future.channel().closeFuture().sync();
        } finally {
            // 优雅的关闭主线程和从线程组
            mainGroup.shutdownGracefully();
            subGroup.shutdownGracefully();

            System.out.println("Netty 服务器已停止");
        }

    }

}
