package uestc.lj.center.mapper;

import uestc.lj.center.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-05-16
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    /**
     * 查询某一天的注册人数
     * @param day
     * @return
     */
    Integer countRegister(String day);
}
