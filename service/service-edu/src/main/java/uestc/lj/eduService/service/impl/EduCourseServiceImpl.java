package uestc.lj.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import uestc.lj.eduService.entity.EduCourse;
import uestc.lj.eduService.entity.EduCourseDescription;
import uestc.lj.eduService.entity.vo.CourseFrontVO;
import uestc.lj.eduService.entity.vo.CourseInfoVO;
import uestc.lj.eduService.entity.vo.CoursePublishVO;
import uestc.lj.eduService.entity.vo.CourseWebFrontVO;
import uestc.lj.eduService.mapper.EduCourseMapper;
import uestc.lj.eduService.service.EduChapterService;
import uestc.lj.eduService.service.EduCourseDescriptionService;
import uestc.lj.eduService.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import uestc.lj.eduService.service.EduVideoService;
import uestc.lj.servicebase.exception.GuliException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-05-14
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private EduCourseService eduCourseService;

    /**
     * 添加课程基本信息
     *
     * @param courseInfoVO
     * @return
     */
    @Override
    public String saveCourseInfo(CourseInfoVO courseInfoVO) {
        // 1.向课程表添加课程基本信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVO, eduCourse);
        int resultCount = baseMapper.insert(eduCourse);
        if (resultCount == 0) {
            throw new GuliException(20001, "课程基本信息添加失败！");
        }
        // 获取课程的id，添加到对应课程的简介中
        String courseId = eduCourse.getId();
        // 2.向课程简介表添加课程简介
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVO.getDescription());
        eduCourseDescription.setId(courseId);
        eduCourseDescriptionService.save(eduCourseDescription);
        return courseId;
    }

    /**
     * 根据课程id查询课程基本信息
     *
     * @param courseId
     * @return
     */
    @Override
    public CourseInfoVO getCourseInfoByCourseId(String courseId) {
        CourseInfoVO courseInfoVO = new CourseInfoVO();
        // 1.查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        BeanUtils.copyProperties(eduCourse, courseInfoVO);
        // 2.查询描述表
        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(courseId);
        courseInfoVO.setDescription(courseDescription.getDescription());
        return courseInfoVO;
    }

    /**
     * 修改课程信息
     *
     * @param courseInfoVO
     */
    @Override
    public void updateCourseInfo(CourseInfoVO courseInfoVO) {
        // 1.修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVO, eduCourse);
        int resultCount = baseMapper.updateById(eduCourse);
        if (resultCount == 0) {
            throw new GuliException(20001, "修改课程信息失败！");
        }
        // 2.修改课程信息描述表
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseInfoVO.getId());
        eduCourseDescription.setDescription(courseInfoVO.getDescription());
        eduCourseDescriptionService.updateById(eduCourseDescription);
    }

    /**
     * 根据课程id获取最终展示课程信息
     *
     * @param id
     * @return
     */
    @Override
    public CoursePublishVO publishCourseInfo(String id) {
        return baseMapper.getPublishCourseInfo(id);
    }

    /**
     * 删除课程所有信息
     *
     * @param courseId
     */
    @Override
    public void removeCourse(String courseId) {
        // 1.根据课程id删除课程中的小节
        eduVideoService.removeVideoByCourseId(courseId);
        // 2.根据课程id删除课程中的章节
        eduChapterService.removeChapterByCourseId(courseId);
        // 3.根据课程id删除课程描述
        eduCourseDescriptionService.removeById(courseId);
        // 4.根据课程id删除课程本身
        int result = baseMapper.deleteById(courseId);

        if (result == 0) {
            throw new GuliException(20001, "课程删除失败！");
        }
    }

    /**
     * 查询前courseCount门热门课程信息
     *
     * @param courseCount
     * @return
     */
    @Override
    @Cacheable(value = "course", key = "'hotCourseList'")
    public List<EduCourse> queryHotCourse(int courseCount) {
        QueryWrapper<EduCourse> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.orderByDesc("view_count");
        courseQueryWrapper.last("limit " + courseCount);
        return eduCourseService.list(courseQueryWrapper);
    }

    /**
     * 条件查询带分页查询课程
     *
     * @param coursePage
     * @param courseFrontVO
     * @return
     */
    @Override
    public Map<String, Object> getFrontCourseList(Page<EduCourse> coursePage, CourseFrontVO courseFrontVO) {

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        // 判断条件值是否为空，不为空则拼接
        if (!StringUtils.isEmpty(courseFrontVO.getSubjectParentId())) {
            // 一级课程分类不为空
            wrapper.eq("subject_parent_id", courseFrontVO.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseFrontVO.getSubjectId())) {
            // 二级课程分类不为空
            wrapper.eq("subject_id", courseFrontVO.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseFrontVO.getBuyCountSort())) {
            // 关注度降序
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVO.getGmtCreateSort())) {
            // 时间最新降序
            wrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(courseFrontVO.getPriceSort())) {
            // 价格降序
            wrapper.orderByDesc("price");
        }

        // 封装查询
        baseMapper.selectPage(coursePage, wrapper);
        //取值
        List<EduCourse> courseList = coursePage.getRecords();
        long current = coursePage.getCurrent();
        long pages = coursePage.getPages();
        long size = coursePage.getSize();
        long total = coursePage.getTotal();
        boolean hasNext = coursePage.hasNext();
        boolean hasPrevious = coursePage.hasPrevious();
        //封装
        Map<String, Object> map = new HashMap<>();
        map.put("items", courseList);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    /**
     * 根据课程id，查询课程基本信息
     *
     * @param courseId
     * @return
     */
    @Override
    public CourseWebFrontVO getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
