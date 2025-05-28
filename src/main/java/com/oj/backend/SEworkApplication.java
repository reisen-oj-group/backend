package com.oj.backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The type S ework application.
 */
@SpringBootApplication
@MapperScan("com.oj.backend.mapper.*")
public class SEworkApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(SEworkApplication.class, args);
    }

}
