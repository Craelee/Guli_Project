package uestc.lj.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import uestc.lj.eduService.entity.EduChapter;
import uestc.lj.eduService.entity.EduVideo;
import uestc.lj.eduService.entity.vo.ChapterVO;
import uestc.lj.eduService.entity.vo.VideoVO;
import uestc.lj.eduService.mapper.EduChapterMapper;
import uestc.lj.eduService.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import uestc.lj.eduService.service.EduVideoService;
import uestc.lj.servicebase.exception.GuliException;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-05-14
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<ChapterVO> getChapterVideoByCourseId(String courseId) {
        // 1.根据课程id查询课程里面所有的章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id", courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);

        // 2.根据课程id查询课程里面所有的小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", courseId);
        List<EduVideo> eduVideoList = eduVideoService.list(wrapperVideo);
        //创建最终集合
        List<ChapterVO> finalList = new ArrayList<>();
        // 3.遍历查询章节list集合进行封装
        for (EduChapter eduChapter : eduChapterList) {
            ChapterVO chapterVO = new ChapterVO();
            BeanUtils.copyProperties(eduChapter, chapterVO);
            finalList.add(chapterVO);

            //创建集合用于封装章节的小节
            List<VideoVO> videoList = new ArrayList<>();

            // 4.遍历查询小节list集合进行封装
            for (EduVideo eduVideo : eduVideoList) {
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {
                    VideoVO videoVO = new VideoVO();
                    BeanUtils.copyProperties(eduVideo, videoVO);
                    videoList.add(videoVO);
                }
            }
            chapterVO.setChildren(videoList);
        }
        return finalList;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        // 1.查询章节下小节的数量
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        int count = eduVideoService.count(wrapper);
        // 2.判读该章节下是否有小节
        if (count > 0) {
            throw new GuliException(20001, "请先删除该章节下的所属小节！");
        }
        int result = baseMapper.deleteById(chapterId);
        return result > 0;
    }

    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        baseMapper.delete(wrapper);
    }
}
