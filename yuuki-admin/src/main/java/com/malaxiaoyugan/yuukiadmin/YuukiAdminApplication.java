package com.malaxiaoyugan.yuukiadmin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages="com")
public class YuukiAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(YuukiAdminApplication.class, args);
    }
}
