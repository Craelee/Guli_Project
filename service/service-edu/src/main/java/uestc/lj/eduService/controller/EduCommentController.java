package uestc.lj.eduService.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import uestc.lj.center.entity.UcenterMember;
import uestc.lj.commonutils.JwtUtils;
import uestc.lj.commonutils.R;
import uestc.lj.eduService.entity.EduComment;
import uestc.lj.eduService.entity.vo.CommentFrontVO;
import uestc.lj.eduService.service.EduCommentService;
import uestc.lj.eduService.service.FeignUcenterService;
import uestc.lj.servicebase.exception.GuliException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-05-18
 */
@RestController
@RequestMapping("/eduService/comment")
public class EduCommentController {
    @Autowired
    private EduCommentService eduCommentService;

    @Autowired
    private FeignUcenterService feignUcenterService;

    /**
     * 根据课程id分页查询课程评论
     *
     * @param page
     * @param limit
     * @return
     */
    @ApiOperation(value = "评论分页列表")
    @PostMapping("pageComment/{page}/{limit}")
    public R pageComment(@ApiParam(name = "page", value = "当前页码", required = true)
                         @PathVariable("page") long page,
                         @ApiParam(name = "limit", value = "每页记录数", required = true)
                         @PathVariable("limit") long limit,
                         @RequestBody(required = false) CommentFrontVO commentFrontVO) {
        Page<EduComment> commentPage = new Page<>(page, limit);
        Map<String, Object> map = eduCommentService.getFrontCommentList(commentPage, commentFrontVO);
        return R.ok().data(map);
    }

    /**
     * 添加评论
     *
     * @param eduComment
     * @param request
     * @return
     */
    @PostMapping("addComment")
    public R addComment(@RequestBody EduComment eduComment, HttpServletRequest request) {
        if (StringUtils.isEmpty(eduComment.getContent())) {
            return R.error().code(20001);
        }
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(memberId)) {
            return R.error().message("您还尚未登录，请先登录后在做评论！").code(20001);
        }

        eduComment.setMemberId(memberId);
        UcenterMember member = feignUcenterService.getInfo(memberId);

        eduComment.setNickname(member.getNickname());
        eduComment.setAvatar(member.getAvatar());

        eduCommentService.save(eduComment);
        return R.ok();
    }
}

