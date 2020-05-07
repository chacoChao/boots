package com.chaco.chao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@Slf4j
@EnableCaching
@ComponentScan(value = {"com.chaco"})
public class ChaoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ChaoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("this is skywalkingtest main method");
    }
}
