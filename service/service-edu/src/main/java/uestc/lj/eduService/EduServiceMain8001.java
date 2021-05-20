package uestc.lj.eduService;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"uestc.lj"})
@EnableDiscoveryClient
@MapperScan(basePackages = "uestc.lj.eduService.mapper")
@EnableFeignClients
public class EduServiceMain8001 {
    public static void main(String[] args) {
        SpringApplication.run(EduServiceMain8001.class, args);
    }
}
