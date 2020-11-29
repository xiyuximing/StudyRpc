package com.cy.rpc.consumer;

import com.cy.rpc.decoder.RpcEncoder;
import com.cy.rpc.entity.RpcRequest;
import com.cy.rpc.handle.UserClientHandler;
import com.cy.rpc.serializer.JsonSerializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RpcConsumer {

    private static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    /**
     * 自定义事件处理器
     */
    private static UserClientHandler userClientHandler;

    public static void initClient() throws InterruptedException {
        userClientHandler = new UserClientHandler();
        //创建连接池对象
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                //通道为nio
                .channel(NioSocketChannel.class)
                //协议为tcp
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline channelPipeline = socketChannel.pipeline();
                        channelPipeline.addLast(new RpcEncoder(new JsonSerializer()))
                                        .addLast(new StringDecoder())
                                        .addLast(userClientHandler);
                    }
                });
        bootstrap.connect("127.0.0.1", 8999).sync();
    }

    public static Object createProxy(Class<?> serviceClass) {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{serviceClass}, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //初始化userClientHandler
                if (userClientHandler == null) {
                    initClient();
                }
                RpcRequest request = new RpcRequest();
                request.setRequestId(UUID.randomUUID().toString());
                request.setMethodName(method.getName());
                request.setClassName(method.getDeclaringClass().getName());
                request.setParameters(args);
                request.setParameterTypes(method.getParameterTypes());
                //设置参数
                userClientHandler.setParam(request);
                //调用方法
                Object result = executorService.submit(userClientHandler).get();
                return result;
            }
        });
    }
}
