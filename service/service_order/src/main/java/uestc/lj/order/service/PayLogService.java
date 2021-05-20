package uestc.lj.order.service;

import uestc.lj.order.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-05-19
 */
public interface PayLogService extends IService<PayLog> {

    /**
     * 根据订单编号生成微信支付二维码
     * @param orderNo
     * @return
     */
    Map createWXPayCode(String orderNo);

    /**
     * 根据订单编号查询订单状态
     * @param orderNo
     * @return
     */
    Map<String, String> queryPayStatus(String orderNo);

    /**
     * 向支付表添加记录同时更新订单状态
     * @param map
     */
    void updateOrderStatus(Map<String, String> map);
}
