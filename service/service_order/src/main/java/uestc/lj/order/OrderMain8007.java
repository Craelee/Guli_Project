package uestc.lj.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "uestc.lj")
@MapperScan(basePackages = "uestc.lj.order.mapper")
@EnableDiscoveryClient
@EnableFeignClients
public class OrderMain8007 {
    public static void main(String[] args) {
        try {
            SpringApplication.run(OrderMain8007.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
