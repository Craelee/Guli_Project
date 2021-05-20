package uestc.lj.eduService.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import uestc.lj.commonutils.R;
import uestc.lj.eduService.entity.EduCourse;
import uestc.lj.eduService.entity.EduTeacher;
import uestc.lj.eduService.entity.vo.CourseInfoVO;
import uestc.lj.eduService.entity.vo.CoursePublishVO;
import uestc.lj.eduService.entity.vo.CourseQuery;
import uestc.lj.eduService.entity.vo.TeacherQuery;
import uestc.lj.eduService.service.EduCourseService;
import uestc.lj.servicebase.exception.GuliException;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-05-14
 */
@RestController
@RequestMapping("/eduService/course")

public class EduCourseController {
    @Autowired
    private EduCourseService eduCourseService;

    /**
     * 添加课程基本信息
     *
     * @param courseInfoVO
     * @return
     */
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVO courseInfoVO) {
        String id = eduCourseService.saveCourseInfo(courseInfoVO);
        return R.ok().data("courseId", id);
    }

    /**
     * 根据课程id查询课程基本信息
     *
     * @param courseId
     * @return
     */
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfoByCourseId(@PathVariable("courseId") String courseId) {
        CourseInfoVO courseInfoVO = eduCourseService.getCourseInfoByCourseId(courseId);
        return R.ok().data("courseInfoVo", courseInfoVO);
    }

    /**
     * 修改课程信息
     *
     * @param courseInfoVO
     * @return
     */
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVO courseInfoVO) {
        eduCourseService.updateCourseInfo(courseInfoVO);
        return R.ok();
    }

    /**
     * 根据课程id获取最终展示课程信息
     *
     * @param id
     * @return
     */
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id) {
        CoursePublishVO coursePublishVO = eduCourseService.publishCourseInfo(id);
        return R.ok().data("publishCourse", coursePublishVO);
    }

    /**
     * 修改课程状态将课程最终发布
     *
     * @param id
     * @return
     */
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        boolean flag = eduCourseService.updateById(eduCourse);
        if (!flag) {
            throw new GuliException(20001, "课程发布失败！");
        }
        return R.ok();
    }

    /**
     * 条件分页查询课程
     *
     * @param current
     * @param limit
     * @param courseQuery
     * @return
     */
    @PostMapping("pageCourseCondition/{current}/{limit}")
    public R pageCourseCondition(@PathVariable("current") long current,
                                 @PathVariable("limit") long limit,
                                 @RequestBody(required = false) CourseQuery courseQuery) {
        Page<EduCourse> pageCourse = new Page<>(current, limit);
        //构建条件
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();

        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(status)) {
            wrapper.like("status", status);
        }


        eduCourseService.page(pageCourse, wrapper);
        //总记录数
        long total = pageCourse.getTotal();
        //数据list集合
        List<EduCourse> records = pageCourse.getRecords();
        return R.ok().data("total", total).data("rows", records);
    }

    /**
     * 分页查询课程列表
     *
     * @param current 当前页
     * @param limit   每页显示数量
     * @return
     */
    @ApiOperation(value = "分页查询课程列表")
    @GetMapping("pageCourse/{current}/{limit}")
    public R pageListCourse(@PathVariable("current") long current,
                            @PathVariable("limit") long limit) {
        Page<EduCourse> pageCourse = new Page<>(current, limit);
        //把分页所有数据封装到pageTeacher对象中
        eduCourseService.page(pageCourse, null);

        //总记录数
        long total = pageCourse.getTotal();
        //数据list
        List<EduCourse> records = pageCourse.getRecords();

        return R.ok().data("total", total).data("rows", records);
    }

    /**
     * 删除课程所有信息
     * @param courseId
     * @return
     */
    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable String courseId) {
        eduCourseService.removeCourse(courseId);
        return R.ok();
    }
}

