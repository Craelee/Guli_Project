package uestc.lj.eduService.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uestc.lj.commonutils.JwtUtils;
import uestc.lj.commonutils.R;
import uestc.lj.eduService.entity.EduCourse;
import uestc.lj.eduService.entity.vo.ChapterVO;
import uestc.lj.eduService.entity.vo.CourseFrontVO;
import uestc.lj.eduService.entity.vo.CourseWebFrontVO;
import uestc.lj.eduService.service.EduChapterService;
import uestc.lj.eduService.service.EduCourseService;
import uestc.lj.eduService.service.FeignOrderService;
import uestc.lj.servicebase.exception.GuliException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduService/coursefront")

public class CourseFrontController {
    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private FeignOrderService feignOrderService;

    /**
     * 条件查询带分页查询课程
     *
     * @param page
     * @param limit
     * @param courseFrontVO
     * @return
     */
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable("page") long page,
                                @PathVariable("limit") long limit,
                                @RequestBody(required = false) CourseFrontVO courseFrontVO) {
        Page<EduCourse> coursePage = new Page<>(page, limit);
        Map<String, Object> map = eduCourseService.getFrontCourseList(coursePage, courseFrontVO);
        return R.ok().data(map);
    }

    /**
     * 获取课程详情
     *
     * @param courseId
     * @return
     */
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable("courseId") String courseId, HttpServletRequest request) {
        // 1.根据课程id，查询课程基本信息
        CourseWebFrontVO courseWebFrontVO = eduCourseService.getBaseCourseInfo(courseId);

        // 2.根据课程id查询章节和小节
        List<ChapterVO> chapterVideoList = eduChapterService.getChapterVideoByCourseId(courseId);

        // 3.根据课程id和用户id查询当前课程是否已经支付过了
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(memberId)) {
            return R.ok().data("courseWebFrontVO", courseWebFrontVO).data("chapterVideoList", chapterVideoList).data("isLogin", 0);
        }
        boolean flag = feignOrderService.isBuyCourse(courseId, JwtUtils.getMemberIdByJwtToken(request));

        return R.ok().data("courseWebFrontVO", courseWebFrontVO).data("chapterVideoList", chapterVideoList).data("isBuy", flag);
    }

    /**
     * 根据课程id查询课程信息
     *
     * @param id
     * @return
     */
    @PostMapping("getCourseInfoOrder/{id}")
    public CourseWebFrontVO getCourseInfoOrder(@PathVariable String id) {
        CourseWebFrontVO courseInfo = eduCourseService.getBaseCourseInfo(id);
        CourseWebFrontVO courseWebFrontVO = new CourseWebFrontVO();
        BeanUtils.copyProperties(courseInfo, courseWebFrontVO);
        return courseWebFrontVO;
    }
}
