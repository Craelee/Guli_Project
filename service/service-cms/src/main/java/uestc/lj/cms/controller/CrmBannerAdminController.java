package uestc.lj.cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import uestc.lj.cms.entity.CrmBanner;
import uestc.lj.cms.service.CrmBannerService;
import uestc.lj.commonutils.R;

/**
 * <p>
 * 首页banner表 后台管理接口
 * </p>
 *
 * @author testjava
 * @since 2021-05-16
 */
@RestController
@RequestMapping("/cms/banneradmin")

public class CrmBannerAdminController {
    @Autowired
    private CrmBannerService crmBannerService;

    /**
     * 分页查询banner
     *
     * @param page
     * @param limit
     * @return
     */
    @ApiOperation(value = "分页查询Banner")
    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable("page") long page, @PathVariable("limit") long limit) {
        Page<CrmBanner> bannerPage = new Page<>(page, limit);
        crmBannerService.page(bannerPage, null);
        return R.ok().data("items", bannerPage.getRecords()).data("total", bannerPage.getTotal());
    }

    /**
     * 添加Banner
     *
     * @param crmBanner
     * @return
     */
    @PostMapping("addBanner")
    @ApiOperation(value = "添加banner")
    public R addBanner(@RequestBody CrmBanner crmBanner) {
        crmBannerService.save(crmBanner);
        return R.ok();
    }

    /**
     * 修改Banner
     *
     * @param crmBanner
     * @return
     */
    @ApiOperation(value = "修改Banner")
    @PutMapping("update")
    public R updateById(@RequestBody CrmBanner crmBanner) {
        crmBannerService.updateById(crmBanner);
        return R.ok();
    }

    /**
     * 删除Banner
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除Banner")
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable String id) {
        crmBannerService.removeById(id);
        return R.ok();
    }

    /**
     * 根据id获取banner
     * @param id
     * @return
     */
    @GetMapping("get/{id}")
    @ApiOperation(value = "获取Banner")
    public R get(@PathVariable String id) {
        CrmBanner banner = crmBannerService.getById(id);
        return R.ok().data("item", banner);
    }

}

