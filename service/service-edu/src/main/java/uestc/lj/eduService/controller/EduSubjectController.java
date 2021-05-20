package uestc.lj.eduService.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import uestc.lj.commonutils.R;
import uestc.lj.eduService.entity.subject.OneSubject;
import uestc.lj.eduService.service.EduSubjectService;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-05-13
 */
@RestController
@RequestMapping("/eduService/subject")

public class EduSubjectController {
    @Autowired
    private EduSubjectService eduSubjectService;

    /**
     * 添加课程分类
     *
     * @param file
     * @return
     */
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {
        eduSubjectService.saveSubject(file, eduSubjectService);
        return R.ok();
    }

    /**
     * 查询所有课程分类
     * @return
     */
    @GetMapping("getAllSubject")
    public R getAllSubject() {
        List<OneSubject> list = eduSubjectService.getAllSubject();
        return R.ok().data("list", list);
    }
}

