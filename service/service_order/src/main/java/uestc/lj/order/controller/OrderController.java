package uestc.lj.order.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import uestc.lj.commonutils.JwtUtils;
import uestc.lj.commonutils.R;
import uestc.lj.order.entity.Order;
import uestc.lj.order.service.OrderService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-05-19
 */
@RestController
@RequestMapping("/order")

public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 生成订单  获取用户信息和课程信息
     *
     * @param courseId
     * @param request
     * @return
     */
    @PostMapping("createOrder/{courseId}")
    public R createOrder(@PathVariable("courseId") String courseId, HttpServletRequest request) {
        // 创建订单，返回订单号
        String orderNum = orderService.createOrder(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderId", orderNum);
    }

    /**
     * 根据订单编号查询订单
     *
     * @param orderId
     * @return
     */
    @GetMapping("getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable("orderId") String orderId) {
        Order order = orderService.getOrderInfo(orderId);
        return R.ok().data("item", order);
    }

    /**
     * 根据课程id和用户id查询订单表中订单状态（用于检查用户是否购买该课程）
     */
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable("courseId") String courseId,
                               @PathVariable("memberId") String memberId) {
        return orderService.isBuyCourse(courseId, memberId);
    }
}

