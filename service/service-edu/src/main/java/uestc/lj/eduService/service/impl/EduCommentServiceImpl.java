package uestc.lj.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;
import uestc.lj.eduService.entity.EduComment;
import uestc.lj.eduService.entity.vo.CommentFrontVO;
import uestc.lj.eduService.mapper.EduCommentMapper;
import uestc.lj.eduService.service.EduCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-05-18
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    /**
     * 根据课程id得到课程评论
     *
     * @param commentPage
     * @return
     */
    @Override
    public Map<String, Object> getFrontCommentList(Page<EduComment> commentPage, CommentFrontVO commentFrontVO) {
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(commentFrontVO.getCourseId())) {
            wrapper.eq("course_id", commentFrontVO.getCourseId());
        }
        //将每条评论的时间设置，并按照时间最新降序排序
        Date date = new Date();
        long createTime = date.getTime();
        commentFrontVO.setGmtCreateSort(createTime);
        wrapper.orderByDesc("gmt_create");

        baseMapper.selectPage(commentPage, wrapper);

        Map<String, Object> map = new HashMap<>();

        List<EduComment> commentList = commentPage.getRecords();
        long current = commentPage.getCurrent();
        long pages = commentPage.getPages();
        long size = commentPage.getSize();
        long total = commentPage.getTotal();
        boolean hasNext = commentPage.hasNext();
        boolean hasPrevious = commentPage.hasPrevious();

        map.put("commentList", commentList);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }
}
