package uestc.lj.cms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "uestc.lj")
@MapperScan(basePackages = "uestc.lj.cms.mapper")
@EnableDiscoveryClient
public class CmsApplication8004 {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication8004.class, args);
    }
}
