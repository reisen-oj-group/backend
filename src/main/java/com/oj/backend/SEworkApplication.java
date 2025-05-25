package com.oj.backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.oj.backend.mapper.*")
public class SEworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(SEworkApplication.class, args);
    }

}
