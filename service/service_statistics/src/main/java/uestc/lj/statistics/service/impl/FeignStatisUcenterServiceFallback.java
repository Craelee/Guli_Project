package uestc.lj.statistics.service.impl;

import org.springframework.stereotype.Service;
import uestc.lj.commonutils.R;
import uestc.lj.servicebase.exception.GuliException;
import uestc.lj.statistics.service.FeignStatisUcenterService;

@Service
public class FeignStatisUcenterServiceFallback implements FeignStatisUcenterService {
    @Override
    public R countRegister(String day) {
        throw new GuliException(20001, "服务器统计繁忙！");
    }
}
