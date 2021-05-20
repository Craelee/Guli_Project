package uestc.lj.eduService.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import uestc.lj.commonutils.R;
import uestc.lj.eduService.entity.EduTeacher;
import uestc.lj.eduService.entity.vo.TeacherQuery;
import uestc.lj.eduService.service.EduTeacherService;

import java.util.List;

/**
 * 讲师 前端控制器
 *
 * @author testjava
 * @since 2021-05-11
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduService/teacher")

public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    /**
     * 查询讲师表所有数据
     *
     * @return 返回所有讲师构成的集合
     */
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher() {
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items", list);
    }

    /**
     * 根据id逻辑删除讲师
     *
     * @param id 讲师id
     * @return 是否删除成功
     */
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable("id") String id) {
        boolean flag = eduTeacherService.removeById(id);
        if (flag) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 分页查询讲师列表
     *
     * @param current 当前页
     * @param limit   每页显示数量
     * @return
     */
    @ApiOperation(value = "分页查询讲师列表")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable("current") long current,
                             @PathVariable("limit") long limit) {
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        //把分页所有数据封装到pageTeacher对象中
        eduTeacherService.page(pageTeacher, null);

        //总记录数
        long total = pageTeacher.getTotal();
        //数据list
        List<EduTeacher> records = pageTeacher.getRecords();

        return R.ok().data("total", total).data("rows", records);
    }

    /**
     * 条件查询带分页的方法
     *
     * @param current
     * @param limit
     * @param teacherQuery
     * @return
     */
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable("current") long current,
                                  @PathVariable("limit") long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery) {
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_modify", end);
        }
        //排序
        wrapper.orderByDesc("gmt_create");

        eduTeacherService.page(pageTeacher, wrapper);
        //总记录数
        long total = pageTeacher.getTotal();
        //数据list集合
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total", total).data("rows", records);
    }

    /**
     * 添加讲师
     *
     * @param eduTeacher
     * @return
     */
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = eduTeacherService.save(eduTeacher);
        if (flag) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 根据讲师id查询讲师
     *
     * @param id
     * @return
     */
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable("id") String id) {
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        if (eduTeacher != null) {
            return R.ok().data("teacher", eduTeacher);
        }
        return R.error();
    }

    /**
     * 更新讲师信息
     *
     * @param eduTeacher
     * @return
     */
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = eduTeacherService.updateById(eduTeacher);
        if (flag) {
            return R.ok();
        }
        return R.error();
    }
}

