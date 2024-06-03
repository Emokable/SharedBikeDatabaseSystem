package com.example.sharedbike;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan
public class sharebikeApplication {

    public static void main(String[] args) {
        SpringApplication.run(sharebikeApplication.class, args);
    }

}
