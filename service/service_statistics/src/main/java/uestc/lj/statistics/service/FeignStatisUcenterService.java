package uestc.lj.statistics.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import uestc.lj.commonutils.R;
import uestc.lj.statistics.service.impl.FeignStatisUcenterServiceFallback;

@Service
@FeignClient(name = "service-ucenter",fallback = FeignStatisUcenterServiceFallback.class)

public interface FeignStatisUcenterService {
    @GetMapping("/center/member/countRegister/{day}")
    public R countRegister(@PathVariable("day") String day);
}
