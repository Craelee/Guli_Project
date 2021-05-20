package uestc.lj.eduService.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import uestc.lj.center.entity.UcenterMember;
import uestc.lj.eduService.service.impl.FeignUcenterServiceFallback;

/**
 * 远程调用member接口
 *
 * @author crazlee
 */
@Service
@FeignClient(name = "service-ucenter", fallback = FeignUcenterServiceFallback.class)
public interface FeignUcenterService {
    @PostMapping("/center/member/getInfoUc/{id}")
    public UcenterMember getInfo(@PathVariable("id") String id);
}
