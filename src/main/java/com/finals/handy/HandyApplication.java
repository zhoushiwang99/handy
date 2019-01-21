package com.finals.handy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)
public class HandyApplication {

    public static void main(String[] args) {
        SpringApplication.run(HandyApplication.class, args);
    }

}

