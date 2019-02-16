package com.finals.handy;

import com.finals.handy.controller.MyWebSocket;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)
public class HandyApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(HandyApplication.class, args);
        MyWebSocket.setApplicationContext(run);
    }

}