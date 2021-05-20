package uestc.lj.center.service;

import uestc.lj.center.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import uestc.lj.center.entity.vo.RegisterVO;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-05-16
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    /**
     * 用户登录
     *
     * @param ucenterMember
     * @return
     */
    String login(UcenterMember ucenterMember);

    /**
     * 用户注册
     *
     * @param registerVO
     */
    void register(RegisterVO registerVO);

    /**
     * 根据微信id获取用户
     * @param openId
     * @return
     */
    UcenterMember getMemberByOpenId(String openId);

    /**
     * 查询某一天的注册人数
     * @param day
     * @return
     */
    Integer countRegister(String day);
}
