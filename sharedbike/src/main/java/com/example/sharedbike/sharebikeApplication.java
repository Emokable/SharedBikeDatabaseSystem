package com.example.sharedbike;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
@EnableSwagger2  //加入EnableSwagger2
@SpringBootApplication
@MapperScan

public class sharebikeApplication {

    public static void main(String [] args) {
        SpringApplication.run(sharebikeApplication.class, args);
    }
}
