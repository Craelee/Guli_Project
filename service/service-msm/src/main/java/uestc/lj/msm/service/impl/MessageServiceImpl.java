package uestc.lj.msm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import uestc.lj.commonutils.R;
import uestc.lj.commonutils.SendMessage;
import uestc.lj.msm.config.ShortMessageProperties;
import uestc.lj.msm.service.MessageService;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private ShortMessageProperties shortMessageProperties;

    /**
     * 给指定的手机号码发送验证码短信
     *
     * @param param
     * @param phoneNum
     * @return
     */
    @Override
    public boolean sendMessage(Map<String, Object> param, String phoneNum) {
        if (StringUtils.isEmpty(phoneNum)) {
            return false;
        }

        DefaultProfile profile =
                DefaultProfile.getProfile("default", "LTAI5tDF7Ufo9ihz5BSs8RCh", "P1XeMOfwrrFDVDTncJYiXBAmKMlqlo");
        IAcsClient client = new DefaultAcsClient(profile);

        //设置相关固定的参数
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        //设置发送相关的参数
        request.putQueryParameter("PhoneNumbers", phoneNum); //手机号
        request.putQueryParameter("SignName", "谷粒在线教育网站"); //申请阿里云 签名名称
        request.putQueryParameter("TemplateCode", "SMS_216843646"); //申请阿里云 模板code
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param)); //验证码数据，转换json数据传递

        try {
            //最终发送
            CommonResponse response = client.getCommonResponse(request);
            boolean success = response.getHttpResponse().isSuccess();
            return success;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean sendMsg(String phoneNum,String code) {
        // 1.发送验证码到指定的手机
        R result = SendMessage.sendMessage(shortMessageProperties.getHost(),
                shortMessageProperties.getPath(),
                code,
                shortMessageProperties.getMethod(),
                phoneNum,
                shortMessageProperties.getAppCode(),
                shortMessageProperties.getSign(),
                shortMessageProperties.getSkin());

        return result.getSuccess();
    }
}
