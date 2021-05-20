package uestc.lj.center.controller;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import uestc.lj.center.entity.UcenterMember;
import uestc.lj.center.entity.vo.RegisterVO;
import uestc.lj.center.service.UcenterMemberService;
import uestc.lj.commonutils.JwtUtils;
import uestc.lj.commonutils.R;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-05-16
 */
@RestController
@RequestMapping("/center/member")

public class UcenterMemberController {
    @Autowired
    private UcenterMemberService ucenterMemberService;

    /**
     * 用户登录
     *
     * @param ucenterMember
     * @return
     */
    @PostMapping("login")
    public R loginUser(@RequestBody UcenterMember ucenterMember) {
        String token = ucenterMemberService.login(ucenterMember);
        return R.ok().data("token", token);
    }

    /**
     * 用户注册
     *
     * @param registerVO
     * @return
     */
    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVO registerVO) {
        ucenterMemberService.register(registerVO);
        return R.ok();
    }

    /**
     * 根据token获取用户信息
     *
     * @param request
     * @return
     */
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request) {
        // 1.调用JWT工具类方法，根据request对象获取头信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        // 2.根据用户id查询用户信息
        UcenterMember ucenterMember = ucenterMemberService.getById(memberId);
        return R.ok().data("userInfo", ucenterMember);
    }


    /**
     * 根据用户id获取用户信息
     *
     * @param id
     * @return
     */
    @PostMapping("getInfoUc/{id}")
    public UcenterMember getInfo(@PathVariable String id) {
        //根据用户id获取用户信息
        UcenterMember ucenterMember = ucenterMemberService.getById(id);
        UcenterMember member = new UcenterMember();
        BeanUtils.copyProperties(ucenterMember, member);
        return member;
    }

    /**
     * 查询某一天的注册人数
     *
     * @param day
     * @return
     */
    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable("day") String day) {
        Integer registerCount = ucenterMemberService.countRegister(day);
        return R.ok().data("countRegister", registerCount);
    }
}

