package uestc.lj.eduService.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uestc.lj.commonutils.R;
import uestc.lj.eduService.entity.EduCourse;
import uestc.lj.eduService.entity.EduTeacher;
import uestc.lj.eduService.service.EduCourseService;
import uestc.lj.eduService.service.EduTeacherService;

import java.util.List;

/**
 * 前台控制器
 *
 * @author crazlee
 */
@RestController
@RequestMapping("/eduService/indexfront")

public class IndexFrontController {
    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduTeacherService eduTeacherService;

    /**
     * 查询数据库中前8条热门课程和前4名热门老师，进行显示
     *
     * @return
     */
    @GetMapping("index")
    public R index() {
        // 1.查询前8的热门课程
        int courseCount = 8;
        List<EduCourse> eduCourseList = eduCourseService.queryHotCourse(courseCount);

        // 2.查询前4的热门老师
        int teacherCount = 4;
        List<EduTeacher> eduTeacherList = eduTeacherService.queryHotTeacher(teacherCount);

        return R.ok().data("eduList", eduCourseList).data("teacherList", eduTeacherList);
    }
}
