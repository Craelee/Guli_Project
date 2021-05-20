package uestc.lj.statistics;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "uestc.lj")
@MapperScan(basePackages = "uestc.lj.statistics.mapper")
@EnableFeignClients
@EnableScheduling
public class StatisticsMain8008 {
    public static void main(String[] args) {
        SpringApplication.run(StatisticsMain8008.class, args);
    }
}
