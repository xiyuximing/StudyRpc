package com.cy.rpc;

import com.cy.rpc.service.impl.UserServiceImpl;

public class bootstrap {
    public static void main(String[] args) throws InterruptedException {
        //启动服务器
        UserServiceImpl.startService("127.0.0.1",8999);
    }
}
