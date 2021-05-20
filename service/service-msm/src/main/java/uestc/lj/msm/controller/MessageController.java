package uestc.lj.msm.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;
import uestc.lj.commonutils.GenerateCode;
import uestc.lj.commonutils.R;
import uestc.lj.msm.service.MessageService;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("eduMsm/msm")

public class MessageController {
    @Autowired
    private MessageService messageService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 给指定的手机号码发送验证码短信
     *
     * @param phoneNum
     * @return
     */
    @GetMapping("send/{phoneNum}")
    public R sendMessage(@PathVariable String phoneNum) {

/*        // 1.从redis获取验证码，如果获取到直接返回
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String code = operations.get(phoneNum);
        if (!StringUtils.isEmpty(code)) {
            return R.ok();
        }*/
        // 2.获取不到则调用方法来发送短信
        // 生成4位验证码
        String code = GenerateCode.getFourBitRandom();
        boolean flag = messageService.sendMsg(phoneNum, code);
        if (flag) {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            // 发送成功之后，放入redis中并设置五分钟有效期
            operations.set(phoneNum, code, 5, TimeUnit.MINUTES);
            return R.ok();
        }
        return R.error().message("短信发送失败！");
    }

}
