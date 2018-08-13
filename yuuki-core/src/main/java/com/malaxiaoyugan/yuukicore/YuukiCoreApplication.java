package com.malaxiaoyugan.yuukicore;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.malaxiaoyugan.yuukicore.mapper")
public class YuukiCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(YuukiCoreApplication.class, args);
    }
}
