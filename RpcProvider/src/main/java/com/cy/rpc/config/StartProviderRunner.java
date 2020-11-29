package com.cy.rpc.config;

import com.cy.rpc.decoder.RpcDecoder;
import com.cy.rpc.entity.RpcRequest;
import com.cy.rpc.handle.UserServiceHandle;
import com.cy.rpc.serializer.JsonSerializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class StartProviderRunner implements ApplicationRunner {

    @Value("${custom.rpc.address}")
    private String address;

    @Value("${custom.rpc.port}")
    private Integer port;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //创建两个线程池对象
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        //创建服务端启动引导对象
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workGroup)
                //设置通道为nio
                .channel(NioServerSocketChannel.class)
                //创建
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        ChannelPipeline channelPipeline = nioSocketChannel.pipeline();
                        //设置String转码
                        channelPipeline.addLast(new StringEncoder());
                        //设置rpc解码
                        channelPipeline.addLast(new RpcDecoder(RpcRequest.class, new JsonSerializer()));
                        channelPipeline.addLast(new UserServiceHandle());
                    }
                });
        bootstrap.bind(address, port).sync();
    }
}
