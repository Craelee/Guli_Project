package uestc.lj.eduService.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.MultipartFile;
import uestc.lj.eduService.entity.EduSubject;
import uestc.lj.eduService.entity.excel.SubjectData;
import uestc.lj.eduService.entity.subject.OneSubject;
import uestc.lj.eduService.entity.subject.TwoSubject;
import uestc.lj.eduService.listener.SubjectExcelListener;
import uestc.lj.eduService.mapper.EduSubjectMapper;
import uestc.lj.eduService.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-05-13
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file, EduSubjectService eduSubjectService) {
        try {
            //获取文件输入流
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, SubjectData.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getAllSubject() {
        // 1.查询所有一级分类
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", "0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);

        // 2.查询所有二级分类
        QueryWrapper<EduSubject> warpperTwo = new QueryWrapper<>();
        warpperTwo.ne("parent_id", "0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(warpperTwo);

        //创建用于存储最终封装数据的list
        List<OneSubject> finalSubjectList = new ArrayList<>();

        // 3.封装一级分类
        for (EduSubject eduSubject : oneSubjectList) {
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(eduSubject, oneSubject);
            finalSubjectList.add(oneSubject);

            List<TwoSubject> twoFinaleSubjectList = new ArrayList<>();
            // 4.封装二级分类
            for (EduSubject subject : twoSubjectList) {
                if (subject.getParentId().equals(eduSubject.getId())) {
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(subject, twoSubject);
                    twoFinaleSubjectList.add(twoSubject);
                }
            }
            //将二级分类分装到其对应的一级分类中
            oneSubject.setChildren(twoFinaleSubjectList);
        }
        return finalSubjectList;
    }
}
