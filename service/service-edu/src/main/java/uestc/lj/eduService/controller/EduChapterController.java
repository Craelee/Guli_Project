package uestc.lj.eduService.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import uestc.lj.commonutils.R;
import uestc.lj.eduService.entity.EduChapter;
import uestc.lj.eduService.entity.vo.ChapterVO;
import uestc.lj.eduService.service.EduChapterService;

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
@RequestMapping("/eduService/chapter")

public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    /**
     * 获取课程大纲列表
     *
     * @return
     */
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable("courseId") String courseId) {
        List<ChapterVO> list = eduChapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo", list);
    }

    /**
     * 添加章节
     *
     * @param eduChapter
     * @return
     */
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter) {
        eduChapterService.save(eduChapter);
        return R.ok();
    }

    /**
     * 更具章节id查询章节
     *
     * @param chapterId
     * @return
     */
    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId) {
        EduChapter eduChapter = eduChapterService.getById(chapterId);
        return R.ok().data("chapter", eduChapter);
    }

    /**
     * 修改章节
     *
     * @param eduChapter
     * @return
     */
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter) {
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }

    /**
     * 删除章节，如果章节中有小节，则不允许直接删除
     *
     * @param chapterId
     * @return
     */
    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable String chapterId) {
        boolean flag = eduChapterService.deleteChapter(chapterId);
        if (flag) {
            return R.ok();
        }
        return R.error();
    }
}

