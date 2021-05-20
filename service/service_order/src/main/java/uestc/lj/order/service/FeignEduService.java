package uestc.lj.order.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import uestc.lj.order.entity.orderfront.CourseWebFrontVO;
import uestc.lj.order.service.impl.FeignEduServiceFallback;
@Service
@FeignClient(value = "service-edu", fallback = FeignEduServiceFallback.class)
public interface FeignEduService {
    @PostMapping("/eduService/coursefront/getCourseInfoOrder/{id}")
    public CourseWebFrontVO getCourseInfoOrder(@PathVariable("id") String id);
}
