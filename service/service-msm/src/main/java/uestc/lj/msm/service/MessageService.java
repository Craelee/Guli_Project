package uestc.lj.msm.service;

import java.util.Map;

public interface MessageService {
    /**
     * 给指定的手机号码发送验证码短信
     *
     * @param param
     * @param phoneNum
     * @return
     */
    boolean sendMessage(Map<String, Object> param, String phoneNum);

    boolean sendMsg(String phoneNum,String code);
}
