package uestc.lj.statistics.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import uestc.lj.commonutils.R;
import uestc.lj.statistics.service.DailyService;
import uestc.lj.statistics.service.FeignStatisUcenterService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-05-19
 */
@RestController
@RequestMapping("/statistics/daily")
public class DailyController {
    @Autowired
    private DailyService dailyService;


    /**
     * 统计某一天的注册人数,生成统计数据
     *
     * @param day
     * @return
     */
    @PostMapping("registerCount/{day}")
    public R registerCount(@PathVariable("day") String day) {
        dailyService.countRegister(day);
        return R.ok();
    }

    /**
     * 显示对应类型的起始数据
     *
     * @param type
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("showData/{type}/{begin}/{end}")
    public R showData(@PathVariable("type") String type,
                      @PathVariable("begin") String begin,
                      @PathVariable("end") String end) {
        Map<String, Object> map = dailyService.showData(type, begin, end);
        return R.ok().data(map);
    }
}

