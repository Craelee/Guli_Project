package uestc.lj.eduService.service.impl;

import org.springframework.stereotype.Service;
import uestc.lj.center.entity.UcenterMember;
import uestc.lj.eduService.service.FeignUcenterService;
import uestc.lj.servicebase.exception.GuliException;

@Service
public class FeignUcenterServiceFallback implements FeignUcenterService {

    @Override
    public UcenterMember getInfo(String id) {
        throw new GuliException(20001, "目前评论人数较多，请稍后重试！");
    }
}
