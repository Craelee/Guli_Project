package uestc.lj.eduService.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import uestc.lj.commonutils.R;
import uestc.lj.eduService.service.impl.FeignVodServiceFallback;

import java.util.List;

@FeignClient(name = "service-vod", fallback = FeignVodServiceFallback.class)
@Service
public interface FeignVodService {
    @DeleteMapping("/eduVod/video/removeVideo/{id}")
    public R deleteVideo(@PathVariable("id") String id);

    @DeleteMapping("/eduVod/video/deleteBatch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);
}
