package com.cy.rpc;

import com.cy.rpc.consumer.RpcConsumer;
import com.cy.rpc.service.IUserService;

public class ClientBootStrap {

    private static final String PROVIDER_NAME = "UserService#sayHello#";

    public static void main(String[] args) throws InterruptedException {
        //1.创建代理对象
        IUserService service = (IUserService) RpcConsumer.createProxy(IUserService.class);

        //2.循环给服务器写数据
        while (true){
            String result = service.sayHello("are you ok !!");
            System.out.println(result);
            Thread.sleep(2000);
        }
    }

}
