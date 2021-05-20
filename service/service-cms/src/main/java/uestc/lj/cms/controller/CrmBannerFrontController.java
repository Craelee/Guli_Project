package uestc.lj.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uestc.lj.cms.entity.CrmBanner;
import uestc.lj.cms.service.CrmBannerService;
import uestc.lj.commonutils.R;

import java.util.List;

/**
 * 前台管理banner接口
 *
 * @author crazlee
 */
@RestController
@RequestMapping("/cms/bannerfront")

public class CrmBannerFrontController {
    @Autowired
    private CrmBannerService crmBannerService;

    /**
     * 获取所有的banner
     * @return
     */
    @GetMapping("getAllBanner")
    public R getAllBanner() {
        List<CrmBanner> bannerList = crmBannerService.selectAllBanner();
        return R.ok().data("list", bannerList);
    }
}
