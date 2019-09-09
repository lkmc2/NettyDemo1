package com.lin;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * 自定义助手类
 *
 * SimpleChannelInboundHandler：对于请求来说，其实相当于【入栈，入境】
 * @author lkmc2
 * @date 2019/9/9 23:47
 */
public class CustomHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        // 获取 Channel 通道
        Channel channel = ctx.channel();

        // 显示客户端的远程地址
        System.out.println(channel.remoteAddress());

        // 定义发送的数据消息
        ByteBuf content = Unpooled.copiedBuffer("Hello Netty~", CharsetUtil.UTF_8);

        // 构建一个 Http Response 响应
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
        // 设置响应的 Header
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

        // 把响应刷到客户端
        ctx.writeAndFlush(response);
    }

}
