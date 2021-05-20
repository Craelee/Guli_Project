package uestc.lj.eduService.service;

import uestc.lj.eduService.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import uestc.lj.eduService.entity.vo.ChapterVO;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-05-14
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVO> getChapterVideoByCourseId(String courseId);

    /**
     * 根据章节id删除指定章节
     * @param chapterId
     * @return
     */
    boolean deleteChapter(String chapterId);

    /**
     * 根据课程id删除课程中的章节
     * @param courseId
     */
    void removeChapterByCourseId(String courseId);
}
