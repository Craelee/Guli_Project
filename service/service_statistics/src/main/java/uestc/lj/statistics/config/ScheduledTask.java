package uestc.lj.statistics.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uestc.lj.statistics.service.DailyService;
import uestc.lj.statistics.utils.DateUtil;

import java.util.Date;

/**
 * 开启定时统计
 *
 * @author crazlee
 */
@Component
public class ScheduledTask {
    @Autowired
    private DailyService dailyService;

    /**
     * 在每天凌晨一点把前一天数据进行数据查询添加
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void ScheduledTask() {
        dailyService.countRegister(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }
}
