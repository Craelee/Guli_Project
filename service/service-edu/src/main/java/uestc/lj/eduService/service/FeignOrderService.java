package uestc.lj.eduService.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import uestc.lj.eduService.service.impl.FeignOrderServiceFallback;

@Service
@FeignClient(name = "service-order", fallback = FeignOrderServiceFallback.class)
public interface FeignOrderService {
    @GetMapping("/order/isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable("courseId") String courseId,
                               @PathVariable("memberId") String memberId);
}
