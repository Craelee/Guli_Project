package uestc.lj.order.service.impl;

import org.springframework.stereotype.Service;

import uestc.lj.order.entity.orderfront.CourseWebFrontVO;
import uestc.lj.order.service.FeignEduService;
import uestc.lj.servicebase.exception.GuliException;

@Service
public class FeignEduServiceFallback implements FeignEduService {
    @Override
    public CourseWebFrontVO getCourseInfoOrder(String id) {
        throw new GuliException(20001, "无法获取课程信息！");
    }
}
