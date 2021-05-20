package uestc.lj.eduService.mapper;

import uestc.lj.eduService.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import uestc.lj.eduService.entity.vo.CoursePublishVO;
import uestc.lj.eduService.entity.vo.CourseWebFrontVO;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-05-14
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    public CoursePublishVO getPublishCourseInfo(String courseId);

    CourseWebFrontVO getBaseCourseInfo(String courseId);
}
