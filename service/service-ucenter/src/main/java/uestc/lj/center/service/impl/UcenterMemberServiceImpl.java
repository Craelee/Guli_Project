package uestc.lj.center.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import uestc.lj.center.entity.UcenterMember;
import uestc.lj.center.entity.vo.RegisterVO;
import uestc.lj.center.mapper.UcenterMemberMapper;
import uestc.lj.center.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import uestc.lj.commonutils.JwtUtils;
import uestc.lj.commonutils.MD5;
import uestc.lj.commonutils.R;
import uestc.lj.servicebase.exception.GuliException;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-05-16
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 用户登录
     *
     * @param ucenterMember
     * @return
     */
    @Override
    public String login(UcenterMember ucenterMember) {
        // 1.获取登录手机号和密码
        String mobile = ucenterMember.getMobile();
        String password = ucenterMember.getPassword();
        // 2.特判
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001, "您的手机号或密码为空！登陆失败！");
        }
        // 3.匹配手机号码
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);
        if (mobileMember == null) {
            throw new GuliException(20001, "查无此用户！");
        }

        // 4.判断用户密码
        String encodedPassword = MD5.encrypt(password);
        if (!mobileMember.getPassword().equals(encodedPassword)) {
            return null;
//      throw new GuliException(20001, "您的密码不正确！请重新输入");
        }

        // 5.判断用户是否禁用
        if (mobileMember.getIsDisabled()) {
            throw new GuliException(20001, "该用户权限已被禁用！");
        }
        // 6.登陆成功之后，利用JWT生成token
        return JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
    }

    /**
     * 用户注册
     *
     * @param registerVO
     */
    @Override
    public void register(RegisterVO registerVO) {
        // 1.获取注册的数据
        String mobile = registerVO.getMobile();
        String code = registerVO.getCode();
        String nickname = registerVO.getNickname();
        String password = registerVO.getPassword();
        // 2.特判
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001, "注册信息有误，请更正！");
        }
        // 3.判断验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode)) {
            throw new GuliException(20001, "您的验证码有误或验证码已过期！");
        }
        // 4.判断手机号码的唯一性
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(wrapper);
        //已经存在该号码
        if (count > 0) {
            throw new GuliException(20001, "该号码已经注册过，请输入新的手机号码！");
        }

        // 5.执行添加
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("https://projectguli.oss-cn-chengdu.aliyuncs.com/2021/05/12/7edb53d74d3b49938b7853ec4a834472file.png");
        //注册成功之后删除验证码
        redisTemplate.opsForValue().set(mobile, code, 1, TimeUnit.MILLISECONDS);
        baseMapper.insert(member);
    }

    /**
     * 根据微信id获取用户
     *
     * @param openId
     * @return
     */
    @Override
    public UcenterMember getMemberByOpenId(String openId) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openId);
        return baseMapper.selectOne(wrapper);
    }

    /**
     * 查询某一天的注册人数
     *
     * @param day
     * @return
     */
    @Override
    public Integer countRegister(String day) {
        return baseMapper.countRegister(day);
    }
}
