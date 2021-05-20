package uestc.lj.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.cache.annotation.Cacheable;
import uestc.lj.cms.entity.CrmBanner;
import uestc.lj.cms.mapper.CrmBannerMapper;
import uestc.lj.cms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-05-16
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    /**
     * 获取热度前2的bannner
     *
     * @return
     */
    @Cacheable(value = "banner", key = "'selectIndexList'")
    @Override
    public List<CrmBanner> selectAllBanner() {
        // 1.根据banner的sort分值进行降序排列，显示前两条记录
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("sort");
        // 2.拼接sql语句
        wrapper.last("limit 4");

        return baseMapper.selectList(wrapper);
    }
}
