package com.cy.rpc.handle;

import com.cy.rpc.entity.RpcRequest;
import com.cy.rpc.service.IUserService;
import com.cy.rpc.service.impl.UserServiceImpl;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutorGroup;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class UserServiceHandle extends ChannelInboundHandlerAdapter  implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    //读取数据时
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        //如果发送的数据为UserService#sayHello#are you ok
//        if (msg.toString().startsWith("UserService")) {
//            IUserService userService = new UserServiceImpl();
//            String result = userService.sayHello(msg.toString().substring(msg.toString().lastIndexOf("#")+1));
//            //3.把调用实现类的方法获得的结果写到客户端
//            ctx.writeAndFlush(result);
//        }
        if (msg instanceof RpcRequest) {
            RpcRequest request = (RpcRequest) msg;
            Object bean = applicationContext.getBean(Class.forName(request.getClassName()));
            FastClass clazz = FastClass.create(bean.getClass());
            FastMethod method = clazz.getMethod(request.getMethodName(), request.getParameterTypes());
            method.invoke(bean, request.getParameters());
            ctx.writeAndFlush("success");
        } else {
            ctx.writeAndFlush("error");
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        UserServiceHandle.applicationContext = applicationContext;
    }
}
