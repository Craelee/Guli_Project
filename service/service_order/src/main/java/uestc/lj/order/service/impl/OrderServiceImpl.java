package uestc.lj.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import uestc.lj.order.entity.Order;
import uestc.lj.order.entity.orderfront.CourseWebFrontVO;
import uestc.lj.order.entity.orderfront.UcenterMember;
import uestc.lj.order.mapper.OrderMapper;
import uestc.lj.order.service.FeignEduService;
import uestc.lj.order.service.FeignOrderUcenterService;
import uestc.lj.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import uestc.lj.order.utils.CreateOrderNoUtils;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-05-19
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private FeignEduService feignEduService;

    @Autowired
    private FeignOrderUcenterService feignOrderUcenterService;

    /**
     * 根据用户id和课程id创建订单
     *
     * @param courseId
     * @param memberId
     * @return
     */
    @Override
    public String createOrder(String courseId, String memberId) {
        // 1.根据远程调用通过用户id获取用户信息
        UcenterMember userInfoOrder = feignOrderUcenterService.getInfo(memberId);
        // 2.根据远程调用通过课程id获取课程信息
        CourseWebFrontVO courseInfoOrder = feignEduService.getCourseInfoOrder(courseId);
        // 3.执行添加
        Order order = new Order();
        // 封装课程属性
        order.setOrderNo(CreateOrderNoUtils.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        // 封装用户属性
        order.setMemberId(memberId);
        order.setMobile(userInfoOrder.getMobile());
        order.setNickname(userInfoOrder.getNickname());
        // 封装订单属性
        // 支付状态：0表示未支付
        order.setStatus(0);
        // 支付类型：1代表微信
        order.setPayType(1);

        baseMapper.insert(order);
        // 4.返回订单号
        return order.getOrderNo();
    }

    /**
     * 根据订单编号查询订单
     *
     * @param orderId
     * @return
     */
    @Override
    public Order getOrderInfo(String orderId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderId);
        return baseMapper.selectOne(wrapper);
    }

    /**
     * 根据课程id和用户id查询订单表中订单状态
     *
     * @param courseId
     * @param memberId
     * @return
     */
    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.eq("member_id", memberId);
        wrapper.eq("status", 1);
        int count = baseMapper.selectCount(wrapper);
        return count > 0;
    }
}
