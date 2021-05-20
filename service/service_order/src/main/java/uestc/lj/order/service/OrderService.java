package uestc.lj.order.service;

import uestc.lj.order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-05-19
 */
public interface OrderService extends IService<Order> {

    /**
     * 根据课程id和用户id创建订单
     * @param courseId
     * @param memberId
     * @return
     */
    String createOrder(String courseId, String memberId);

    /**
     * 根据订单编号查询订单
     * @param orderId
     * @return
     */
    Order getOrderInfo(String orderId);

    /**
     * 根据课程id和用户id查询订单表中订单状态
     * @param courseId
     * @param memberId
     * @return
     */
    boolean isBuyCourse(String courseId, String memberId);
}
