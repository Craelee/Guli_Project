package uestc.lj.order.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import uestc.lj.commonutils.R;
import uestc.lj.order.service.PayLogService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-05-19
 */
@RestController
@RequestMapping("/order/paylog")

public class PayLogController {
    @Autowired
    private PayLogService payLogService;

    /**
     * 根据订单号生成微信支付二维码接口
     *
     * @param orderNo
     * @return
     */
    @GetMapping("createWXPayCode/{orderNo}")
    public R createWXPayCode(@PathVariable("orderNo") String orderNo) {
        Map map = payLogService.createWXPayCode(orderNo);
        return R.ok().data(map);
    }

    /**
     * 根据订单编号查询订单状态
     *
     * @param orderNo
     * @return
     */
    @GetMapping("queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable("orderNo") String orderNo) {
        Map<String, String> map = payLogService.queryPayStatus(orderNo);
        if (map == null) {
            return R.error().message("支付出错了！");
        }
        //如果返回map里面不为空，通过map获取订单状态
        if (map.get("trade_state").equals("SUCCESS")) {
            //添加记录到支付表中，并更新订单表订单状态
            payLogService.updateOrderStatus(map);
            return R.ok().message("支付完成！");
        }
        return R.ok().code(20005).message("支付中...");
    }
}

