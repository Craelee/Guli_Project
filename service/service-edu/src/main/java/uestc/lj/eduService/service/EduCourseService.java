package uestc.lj.eduService.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import uestc.lj.eduService.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import uestc.lj.eduService.entity.vo.CourseFrontVO;
import uestc.lj.eduService.entity.vo.CourseInfoVO;
import uestc.lj.eduService.entity.vo.CoursePublishVO;
import uestc.lj.eduService.entity.vo.CourseWebFrontVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-05-14
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 添加课程基本信息
     *
     * @param courseInfoVO
     * @return
     */
    String saveCourseInfo(CourseInfoVO courseInfoVO);

    /**
     * 根据课程id查询课程基本信息
     *
     * @param courseId
     * @return
     */
    CourseInfoVO getCourseInfoByCourseId(String courseId);

    /**
     * 修改课程信息
     *
     * @param courseInfoVO
     */
    void updateCourseInfo(CourseInfoVO courseInfoVO);

    /**
     * 根据课程id获取最终展示课程信息
     *
     * @param id
     * @return
     */
    CoursePublishVO publishCourseInfo(String id);

    /**
     * 删除课程所有信息
     *
     * @param courseId
     */
    void removeCourse(String courseId);

    /**
     * 查询前courseCount门热门课程信息
     * @return
     * @param courseCount
     */
    List<EduCourse> queryHotCourse(int courseCount);

    /**
     * 条件查询带分页查询课程
     * @param coursePage
     * @param courseFrontVO
     * @return
     */
    Map<String, Object> getFrontCourseList(Page<EduCourse> coursePage, CourseFrontVO courseFrontVO);

    /**
     * 根据课程id，查询课程基本信息
     * @param courseId
     * @return
     */
    CourseWebFrontVO getBaseCourseInfo(String courseId);
}
