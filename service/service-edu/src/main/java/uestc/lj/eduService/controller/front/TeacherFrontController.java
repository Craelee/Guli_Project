package uestc.lj.eduService.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uestc.lj.commonutils.R;
import uestc.lj.eduService.entity.EduCourse;
import uestc.lj.eduService.entity.EduTeacher;
import uestc.lj.eduService.service.EduCourseService;
import uestc.lj.eduService.service.EduTeacherService;

import java.util.List;
import java.util.Map;

/**
 * 前端教师控制器
 *
 * @author crazlee
 */
@RestController
@RequestMapping("/eduService/teacherfront")

public class TeacherFrontController {
    @Autowired
    private EduTeacherService eduTeacherService;

    @Autowired
    private EduCourseService eduCourseService;

    /**
     * 分页查询讲师
     *
     * @param page
     * @param limit
     * @return
     */
    @PostMapping("getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable("page") long page,
                                 @PathVariable("limit") long limit) {
        Page<EduTeacher> teacherPage = new Page<>(page, limit);
        Map<String, Object> map = eduTeacherService.getTeacherFrontList(teacherPage);
        // 返回分页的所有数据
        return R.ok().data(map);
    }

    /**
     * 根据讲师id获取讲师详情
     *
     * @param teacherId
     * @return
     */
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable("teacherId") String teacherId) {
        // 1.根据讲师id查询讲师基本信息
        EduTeacher eduTeacher = eduTeacherService.getById(teacherId);
        // 2.根据讲师id查询所讲课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", teacherId);
        List<EduCourse> courseList = eduCourseService.list(wrapper);

        return R.ok().data("teacher", eduTeacher).data("courseList", courseList);
    }
}
