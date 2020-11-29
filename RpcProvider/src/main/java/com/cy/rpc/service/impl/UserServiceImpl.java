package com.cy.rpc.service.impl;

import com.cy.rpc.handle.UserServiceHandle;
import com.cy.rpc.service.IUserService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
    public String sayHello(String word) {
        System.out.println("收到数据" + word);
        return "服务器返回数据" + word;
    }

//    //创建启动服务器方法
//    public static void startService(String ip, int port) throws InterruptedException {
//        //创建两个线程池对象
//        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
//        NioEventLoopGroup workGroup = new NioEventLoopGroup();
//        //创建服务端启动引导对象
//        ServerBootstrap bootstrap = new ServerBootstrap();
//        bootstrap.group(bossGroup, workGroup)
//                //设置通道为nio
//                .channel(NioServerSocketChannel.class)
//                //创建
//                .childHandler(new ChannelInitializer<NioSocketChannel>() {
//                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
//                        ChannelPipeline channelPipeline = nioSocketChannel.pipeline();
//                        //设置String解码
//                        channelPipeline.addLast(new StringEncoder());
//                        channelPipeline.addLast(new StringDecoder());
//
//                        channelPipeline.addLast(new UserServiceHandle());
//                    }
//                });
//        bootstrap.bind(port).sync();
//    }
}
