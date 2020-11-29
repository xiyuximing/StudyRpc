package com.cy.rpc;

import com.cy.rpc.service.impl.UserServiceImpl;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Bootstrap {
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Bootstrap.class, args);
    }
}
