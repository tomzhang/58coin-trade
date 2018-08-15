package com.coin58.api;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author coin58
 * @date 2017/5/5
 */
@SpringBootApplication
@EnableScheduling
public class Coin58APIApp {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Coin58APIApp.class)
            .bannerMode(Mode.OFF)
            .run(args);
    }
}
