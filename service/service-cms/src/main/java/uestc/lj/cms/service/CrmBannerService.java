package uestc.lj.cms.service;

import uestc.lj.cms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-05-16
 */
public interface CrmBannerService extends IService<CrmBanner> {

    /**
     * 获取所有的banner
     * @return
     */
    List<CrmBanner> selectAllBanner();
}
