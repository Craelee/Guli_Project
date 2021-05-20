package uestc.lj.order.utils;

import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 随机生成唯一订单号
 * @author crazlee
 */
public class CreateOrderNoUtils {
    /**
     * 获取订单号
     * @return
     */
    public static String getOrderNo() {
//        Date date = new Date();
//        long time = date.getTime();
//        String newDate = String.valueOf(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate = sdf.format(new Date());
        String result = "";
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            result += random.nextInt(10);
        }
        return newDate + result;
    }
}
