package com.lin.ch01;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 服务初始化器，channel 注册后，会执行里面相应的初始化方法
 * @author lkmc2
 * @date 2019/9/9 23:40
 */
public class HelloServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        // 通过 SocketChannel 去获得对应的管道（pipeline）
        ChannelPipeline pipeline = channel.pipeline();

        // 通过管道，添加 Http Handler
        // HttpServerCodec 是 Netty 自己提供的助手类，可以理解拦截器
        // 当请求到服务端，我们需要做解码，响应到客户端做编码
        pipeline.addLast("HttpServerCodec", new HttpServerCodec());

        // 通过管道，添加自定义的事件处理器（自定义助手类），返回字符串“hello netty”
        pipeline.addLast("customHandler", new CustomHandler());
    }

}
