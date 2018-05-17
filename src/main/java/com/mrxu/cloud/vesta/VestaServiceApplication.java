package com.mrxu.cloud.vesta;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author ifocusing-xuzhiwei
 * @since 2018/5/16
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.mrxu.cloud.*"})
public class VestaServiceApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(VestaServiceApplication.class).web(true).run(args);
    }
}
