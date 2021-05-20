package uestc.lj.order.service.impl;

import org.springframework.stereotype.Service;
import uestc.lj.order.entity.orderfront.UcenterMember;
import uestc.lj.order.service.FeignOrderUcenterService;

import uestc.lj.servicebase.exception.GuliException;


@Service
public class FeignOrderUcenterServiceFallback implements FeignOrderUcenterService {
    @Override
    public UcenterMember getInfo(String id) {
        throw new GuliException(20001, "无法获取用户信息！");
    }
}
