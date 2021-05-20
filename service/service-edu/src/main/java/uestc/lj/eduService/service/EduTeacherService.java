package uestc.lj.eduService.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import uestc.lj.eduService.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-05-11
 */
public interface EduTeacherService extends IService<EduTeacher> {

    /**
     * 查询前teacherCount名热门老师
     * @return
     */
    List<EduTeacher> queryHotTeacher(int teacherCount);

    /**
     * 分页查询讲师
     * @param teacherPage
     * @return
     */
    Map<String, Object> getTeacherFrontList(Page<EduTeacher> teacherPage);
}
