package uestc.lj.commonutils;

import com.aliyun.api.gateway.demo.util.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;

import java.util.HashMap;
import java.util.Map;

/**
 * 发送短信的工具类
 *
 * @author crazlee
 */
public class SendMessage {

    /**
     * 调用第三方接口api发送短信验证码业务方法
     *
     * @param host     短信接口调用的URL地址
     * @param path     具体发送短信功能的地址
     * @param method   请求方式
     * @param phoneNum 要发送的手机号
     * @param appCode  调用第三方接口的appcode
     * @param sign     第三方签名编号
     * @param skin     第三方签名模板编号
     * @return 返回调用结果是否成功及失败的消息
     */
    public static R sendMessage(String host, String path, String code, String method, String phoneNum, String appCode, String sign, String skin) {

        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appCode);

        //封装其他参数
        Map<String, String> querys = new HashMap<String, String>();

        //需要发送的手机号
        querys.put("mobile", phoneNum);

        //要发送的验证码，也就是模板中会变化的部分
        querys.put("param", "**code**:【" + code + "】,**minute**:5");

        //签名ID
        querys.put("smsSignId", sign);

        //模板ID
        querys.put("templateId", skin);
        Map<String, String> bodys = new HashMap<>();


        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);

            StatusLine statusLine = response.getStatusLine();

            int statusCode = statusLine.getStatusCode();

            String reasonPhrase = statusLine.getReasonPhrase();

            if (statusCode == 200) {
                //操作成功，把生成的验证码返回
                return R.ok();
            }
            return R.error().message(reasonPhrase);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error().message(e.getMessage());
        }
    }
}
