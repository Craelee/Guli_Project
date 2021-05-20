package uestc.lj.order.service;



import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import uestc.lj.order.entity.orderfront.UcenterMember;
import uestc.lj.order.service.impl.FeignOrderUcenterServiceFallback;


@Service
@FeignClient(name = "service-ucenter", fallback = FeignOrderUcenterServiceFallback.class)
public interface FeignOrderUcenterService {
    @PostMapping("/center/member/getInfoUc/{id}")
    public UcenterMember getInfo(@PathVariable("id") String id);
}
