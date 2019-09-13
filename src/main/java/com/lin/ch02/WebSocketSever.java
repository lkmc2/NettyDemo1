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
        NioEventLoopGroup mainGroup = new NioEventLoopGroup();
        NioEventLoopGroup subGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(mainGroup, subGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(null);

            ChannelFuture future = server.bind(9999).sync();

            future.channel().closeFuture().sync();
        } finally {
            mainGroup.shutdownGracefully();
            subGroup.shutdownGracefully();
        }

    }

}
