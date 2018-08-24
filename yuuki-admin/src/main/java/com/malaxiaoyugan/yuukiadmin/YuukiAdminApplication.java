package com.malaxiaoyugan.yuukiadmin;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;


@SpringBootApplication()
@ComponentScan(basePackages ="com")
@ComponentScan(basePackages ="service")
@ComponentScan(basePackages ="controller")
public class YuukiAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(YuukiAdminApplication.class, args);
    }
}
