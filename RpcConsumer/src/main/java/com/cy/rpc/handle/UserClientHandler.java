package com.cy.rpc.handle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

public class UserClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    /**
     * 事件处理器上下文对象
     */
    private ChannelHandlerContext context;

    /**
     * 服务器返回的数据
     */
    private String result;

    /**
     * 将要发送给服务器的数据
     */
    //private String param;

    private Object param;

    //服务端和客户端连接时该方法执行
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.context = ctx;
    }

    //当我们读到服务器数据,该方法自动执行
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        result = msg.toString();
        notify();
    }

    //将客户端的数写到服务器
    public synchronized Object call() throws Exception {
        context.writeAndFlush(param);
        wait();
        return result;
    }

    public void setParam(Object param) {
        this.param = param;
    }
}
