package com.romantupikov.yetanothersite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class YetAnotherSiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(YetAnotherSiteApplication.class, args);
    }
}
