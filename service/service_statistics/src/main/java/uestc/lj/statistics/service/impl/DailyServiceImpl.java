package uestc.lj.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import uestc.lj.commonutils.R;
import uestc.lj.statistics.entity.Daily;
import uestc.lj.statistics.mapper.DailyMapper;
import uestc.lj.statistics.service.DailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import uestc.lj.statistics.service.FeignStatisUcenterService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-05-19
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {

    @Autowired
    private FeignStatisUcenterService feignStatisUcenterService;

    /**
     * 统计某一天的注册人数,生成统计数据
     *
     * @param day
     */
    @Override
    public void countRegister(String day) {
        //更正：确保添加记录的唯一性
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        baseMapper.delete(wrapper);

        // 1.远程调用某一天的注册人数
        R r = feignStatisUcenterService.countRegister(day);
        int registerNum = (int) r.getData().get("countRegister");
        // 获取统计信息(模拟实现)
        // TODO
        int loginNum = RandomUtils.nextInt(100, 200);
        int videoViewNum = RandomUtils.nextInt(100, 200);
        int courseNum = RandomUtils.nextInt(100, 200);
        // 2.封装数据
        Daily daily = new Daily();
        daily.setRegisterNum(registerNum);
        daily.setLoginNum(loginNum);
        daily.setVideoViewNum(videoViewNum);
        daily.setCourseNum(courseNum);
        daily.setDateCalculated(day);
        // 3.执行数据库添加
        baseMapper.insert(daily);
    }

    /**
     * 显示对应类型的起始数据
     *
     * @param type
     * @param begin
     * @param end
     * @return
     */
    @Override
    public Map<String, Object> showData(String type, String begin, String end) {
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated", begin, end);
        wrapper.select("date_calculated", type);

        List<Daily> dailyList = baseMapper.selectList(wrapper);

        List<String> dateCalculatedList = new ArrayList<>();
        List<Integer> numDataList = new ArrayList<>();

        for (Daily daily : dailyList) {
            //封装指定日期集合
            dateCalculatedList.add(daily.getDateCalculated());
            // 封装对应类型的数量
            switch (type) {
                case "login_num":
                    numDataList.add(daily.getLoginNum());
                    break;
                case "register_num":
                    numDataList.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    numDataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    numDataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("date_calculatedList", dateCalculatedList);
        map.put("numDataList", numDataList);
        return map;
    }
}
