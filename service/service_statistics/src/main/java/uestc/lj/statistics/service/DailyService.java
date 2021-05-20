package uestc.lj.statistics.service;

import uestc.lj.statistics.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-05-19
 */
public interface DailyService extends IService<Daily> {

    /**
     * 统计某一天的注册人数,生成统计数据
     * @param day
     */
    void countRegister(String day);

    /**
     * 显示对应类型的起始数据
     * @param type
     * @param begin
     * @param end
     * @return
     */
    Map<String, Object> showData(String type, String begin, String end);
}
