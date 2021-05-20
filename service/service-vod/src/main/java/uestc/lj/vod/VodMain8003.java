package uestc.lj.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = "uestc.lj")
@EnableDiscoveryClient
public class VodMain8003 {
    public static void main(String[] args) {
        SpringApplication.run(VodMain8003.class, args);
    }
}

