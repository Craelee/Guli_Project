package uestc.lj.center;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "uestc.lj")
@MapperScan(basePackages = "uestc.lj.center.mapper")
public class UcenterMain8150 {
    public static void main(String[] args) {
        SpringApplication.run(UcenterMain8150.class, args);
    }
}
