package uestc.lj.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import uestc.lj.eduService.entity.EduTeacher;
import uestc.lj.eduService.mapper.EduTeacherMapper;
import uestc.lj.eduService.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-05-11
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Autowired
    private EduTeacherService eduTeacherService;

    /**
     * 查询前teacherCount名热门教师
     *
     * @return
     */
    @Override
    @Cacheable(value = "teacher", key = "'hotTeacherList'")
    public List<EduTeacher> queryHotTeacher(int teacherCount) {
        QueryWrapper<EduTeacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.orderByDesc("sort");
        teacherQueryWrapper.last("limit " + teacherCount);
        return eduTeacherService.list(teacherQueryWrapper);
    }

    /**
     * 分页查询讲师
     *
     * @param teacherPage
     * @return
     */
    @Override
    public Map<String, Object> getTeacherFrontList(Page<EduTeacher> teacherPage) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("sort");
        //把分页数据封装到teacherPage对象中
        baseMapper.selectPage(teacherPage, wrapper);
        //把分页数据获取出来，放到map集合
        //每页数据的list集合
        List<EduTeacher> teacherList = teacherPage.getRecords();
        //当前页
        long current = teacherPage.getCurrent();
        //总页数
        long pages = teacherPage.getPages();
        //每页记录数
        long size = teacherPage.getSize();
        //总记录数
        long total = teacherPage.getTotal();
        //是否有下一页
        boolean hasNext = teacherPage.hasNext();
        //是否有上一页
        boolean hasPrevious = teacherPage.hasPrevious();

        Map<String, Object> map = new HashMap<>();
        map.put("items", teacherList);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }
}
