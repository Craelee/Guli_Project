package uestc.lj.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import uestc.lj.eduService.entity.EduVideo;
import uestc.lj.eduService.mapper.EduVideoMapper;
import uestc.lj.eduService.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import uestc.lj.eduService.service.FeignVodService;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-05-14
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private FeignVodService feignVodService;

    /**
     * 根据课程id删除课程中的小节及其视频
     *
     * @param courseId
     */
    @Override

    public void removeVideoByCourseId(String courseId) {
        // 1.根据课程id查询课程所有的视频id
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", courseId);
        wrapperVideo.select("video_source_id");
        List<EduVideo> eduVideoList = baseMapper.selectList(wrapperVideo);

        List<String> videoIds = new ArrayList<>();
        for (EduVideo eduVideo : eduVideoList) {
            if (!StringUtils.isEmpty(eduVideo.getVideoSourceId())) {
                videoIds.add(eduVideo.getVideoSourceId());
            }
        }
        if (videoIds.size() > 0) {
            feignVodService.deleteBatch(videoIds);
        }

        // 2.删除小节
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        baseMapper.delete(wrapper);
    }
}
