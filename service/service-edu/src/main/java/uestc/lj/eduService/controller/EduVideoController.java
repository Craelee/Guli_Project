package uestc.lj.eduService.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import uestc.lj.commonutils.R;
import uestc.lj.eduService.entity.EduVideo;
import uestc.lj.eduService.service.EduVideoService;
import uestc.lj.eduService.service.FeignVodService;
import uestc.lj.servicebase.exception.GuliException;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-05-14
 */
@RestController
@RequestMapping("/eduService/video")

public class EduVideoController {
    @Autowired
    private EduVideoService eduVideoService;
    @Autowired
    private FeignVodService feignVodService;

    /**
     * 添加小节
     *
     * @param eduVideo
     * @return
     */
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        eduVideoService.save(eduVideo);
        return R.ok();
    }

    /**
     * 删除小节要同时删除小节中的视频
     *
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id) {
        //根据小节id获取视频的id
        EduVideo eduVideo = eduVideoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)) {
            R result = feignVodService.deleteVideo(videoSourceId);
            if (result.getCode() == 20001) {
                throw new GuliException(20001, "服务器处理有延迟，视频删除失败！");
            }
        }
        eduVideoService.removeById(id);
        return R.ok();
    }

    /**
     * 根据id查询小节
     *
     * @param id
     * @return
     */
    @GetMapping("getVideoInfo/{id}")
    public R getVideoInfo(@PathVariable String id) {
        EduVideo eduVideo = eduVideoService.getById(id);
        return R.ok().data("video", eduVideo);
    }

    /**
     * 更新小节信息
     *
     * @param eduVideo
     * @return
     */
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo) {
        eduVideoService.updateById(eduVideo);
        return R.ok();
    }
}

