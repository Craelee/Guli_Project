package uestc.lj.eduService.service;

import org.springframework.web.multipart.MultipartFile;
import uestc.lj.eduService.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import uestc.lj.eduService.entity.subject.OneSubject;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-05-13
 */
public interface EduSubjectService extends IService<EduSubject> {

    /**
     * 添加课程分类
     * @param file
     * @param eduSubjectService
     */
    void saveSubject(MultipartFile file, EduSubjectService eduSubjectService);

    /**
     * 查询所有课程分类
     * @return
     */
    List<OneSubject> getAllSubject();

}
