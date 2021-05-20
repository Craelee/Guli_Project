package uestc.lj.eduService.service;

import uestc.lj.eduService.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-05-14
 */
public interface EduVideoService extends IService<EduVideo> {

    /**
     * 根据课程id删除课程中的小节
     * @param courseId
     */
    void removeVideoByCourseId(String courseId);
}
