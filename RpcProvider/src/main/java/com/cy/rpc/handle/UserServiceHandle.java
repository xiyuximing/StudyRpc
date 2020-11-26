package com.cy.rpc.handle;

import com.cy.rpc.service.IUserService;
import com.cy.rpc.service.impl.UserServiceImpl;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutorGroup;

public class UserServiceHandle extends ChannelInboundHandlerAdapter {

    //读取数据时
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //如果发送的数据为UserService#sayHello#are you ok
        if (msg.toString().startsWith("UserService")) {
            IUserService userService = new UserServiceImpl();
            String result = userService.sayHello(msg.toString().substring(msg.toString().lastIndexOf("#")+1));
            //3.把调用实现类的方法获得的结果写到客户端
            ctx.writeAndFlush(result);
        }
    }
}
