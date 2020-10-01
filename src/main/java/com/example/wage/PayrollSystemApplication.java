package com.example.wage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@MapperScan("com.example.wage.mapper.**")
public class PayrollSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayrollSystemApplication.class, args);
    }

}
