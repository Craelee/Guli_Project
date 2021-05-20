package uestc.lj.center.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uestc.lj.center.entity.UcenterMember;
import uestc.lj.center.service.UcenterMemberService;
import uestc.lj.center.utils.ConstantWXUtils;
import uestc.lj.center.utils.HttpClientUtils;
import uestc.lj.commonutils.JwtUtils;
import uestc.lj.servicebase.exception.GuliException;

import java.net.URLEncoder;
import java.util.HashMap;


@CrossOrigin
 //只是请求地址，不需要返回数据
@RequestMapping("/api/ucenter/wx")
public class WxApiController {
    @Autowired
    private UcenterMemberService ucenterMemberService;

    /**
     * 生成微信扫描二维码
     *
     * @return
     */
    @GetMapping("login")
    public String getWxCode() {
        // 微信开放平台授权baseUrl  %s相当于?代表占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        //对redirect_url进行URLEncoder编码
        String redirectUrl = ConstantWXUtils.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //设置%s里面值
        String url = String.format(
                baseUrl,
                ConstantWXUtils.WX_OPEN_APP_ID,
                redirectUrl,
                "guliUestc"
        );

        //重定向到请求微信地址里面
        return "redirect:" + url;
    }

    /**
     * 获取到登录信息之后，跳转页面,并获取登录人信息
     *
     * @return
     */
    @GetMapping("callback")
    public String callback(String code, String state) {
        try {
            // 1.获取code值，临时票据，类似于验证码
            // 2.拿着code请求 微信固定的地址，得到两个值 accsss_token 和 openid
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            // 3.拼接三个参数 ：id  秘钥 和 code值
            String accessTokenUrl = String.format(
                    baseAccessTokenUrl,
                    ConstantWXUtils.WX_OPEN_APP_ID,
                    ConstantWXUtils.WX_OPEN_APP_SECRET,
                    code
            );
            // 4.请求这个拼接好的地址，得到返回的两个值access_token和openid(使用httpclient发送请求，得到返回结果)
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            System.out.println("accessTokenInfo---->" + accessTokenInfo);
            // 5.从accessTokenInfo字符串获取出来两个值 access_token和openid(将其转换成map集合，再从map中根据key获取对应值)
            Gson gson = new Gson();
            HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String accessToken = (String) mapAccessToken.get("access_token");
            String openId = (String) mapAccessToken.get("openid");

            // 10.将用户添加到数据库
            UcenterMember ucenterMember = ucenterMemberService.getMemberByOpenId(openId);
            if (ucenterMember == null) {
                // 6.拿着得到access_token 和 openid，再去请求微信提供固定的地址，获取到扫描人信息
                //访问微信的资源服务器，获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                // 7.拼接两个参数
                String userInfoUrl = String.format(
                        baseUserInfoUrl,
                        accessToken,
                        openId
                );
                // 8.发送请求获取到登录信息
                String userInfo = HttpClientUtils.get(userInfoUrl);

                // 9.获取返回userinfo字符串扫描人信息
                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                //昵称
                String nickname = (String) userInfoMap.get("nickname");
                //头像
                String headimgurl = (String) userInfoMap.get("headimgurl");

                // 执行添加
                ucenterMember = new UcenterMember();
                ucenterMember.setNickname(nickname);
                ucenterMember.setOpenid(openId);
                ucenterMember.setAvatar(headimgurl);
                ucenterMemberService.save(ucenterMember);
            }
            //使用jwt根据member对象生成token字符串
            String jwtToken = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());
            //最后：返回首页面，通过路径传递token字符串
            return "redirect:http://localhost:3000?token=" + jwtToken;
        } catch (Exception e) {
            throw new GuliException(20001, "登录失败");
        }
    }
}
