package com.romantupikov.rssfeed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class RssFeedApplication {

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext ctx = new SpringApplication(RssFeedApplication.class).run(args);
        System.out.println("Press key to exit...");
        System.in.read();
        ctx.close();
    }

}
