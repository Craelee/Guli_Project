package uestc.lj.eduService.service.impl;

import org.springframework.stereotype.Service;
import uestc.lj.eduService.service.FeignOrderService;
import uestc.lj.servicebase.exception.GuliException;

@Service
public class FeignOrderServiceFallback implements FeignOrderService {
    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        throw new GuliException(20001, "目前查询人数过多！请稍后！");
    }
}
