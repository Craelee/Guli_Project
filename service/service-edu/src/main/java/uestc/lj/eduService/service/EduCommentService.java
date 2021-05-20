package uestc.lj.eduService.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import uestc.lj.eduService.entity.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;
import uestc.lj.eduService.entity.vo.CommentFrontVO;

import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-05-18
 */
public interface EduCommentService extends IService<EduComment> {

    /**
     * 根据课程id得到课程评论
     *
     * @param commentPage
     * @return
     */
    Map<String, Object> getFrontCommentList(Page<EduComment> commentPage, CommentFrontVO commentFrontVO);
}
