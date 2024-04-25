package com.mosetian.AliYunAutoSignIn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // 开启定时任务支持
public class AliYunAutoSignInApplication {

    public static void main(String[] args) {
        SpringApplication.run(AliYunAutoSignInApplication.class, args);
    }

}
