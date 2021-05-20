package uestc.lj.eduService.service.impl;

import org.springframework.stereotype.Service;
import uestc.lj.commonutils.R;
import uestc.lj.eduService.service.FeignVodService;

import java.util.List;

@Service
public class FeignVodServiceFallback implements FeignVodService {
    @Override
    public R deleteVideo(String id) {
        return R.error().message("删除视频过程出错了！");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("批量删除视频过程出错了！");
    }
}
