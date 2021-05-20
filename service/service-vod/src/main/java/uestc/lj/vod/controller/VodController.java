package uestc.lj.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uestc.lj.commonutils.R;
import uestc.lj.servicebase.exception.GuliException;
import uestc.lj.vod.service.VodService;
import uestc.lj.vod.utils.ConstantVodUtils;
import uestc.lj.vod.utils.InitObject;

import java.util.List;

@RestController

@RequestMapping("eduVod/video")
public class VodController {

    @Autowired
    private VodService vodService;

    /**
     * 上传视频到阿里云
     *
     * @param file
     * @return
     */
    @PostMapping("uploadVideo")
    public R uploadVideo(MultipartFile file) {
        String videoId = vodService.uploadVideo(file);
        return R.ok().data("videoId", videoId);
    }

    /**
     * 删除小节中的视频
     *
     * @param id
     * @return
     */
    @DeleteMapping("removeVideo/{id}")
    public R deleteVideo(@PathVariable String id) {
        try {
            // 1.初始化对象
            DefaultAcsClient client = InitObject.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            // 2.创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            // 3.向request设置视频id
            request.setVideoIds(id);
            // 4.调用初始化对象的方法实现删除
            client.getAcsResponse(request);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001, "无法删除小节中的视频！");
        }
    }

    /**
     * 删除多个视频
     *
     * @return
     */
    @DeleteMapping("deleteBatch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList) {
        vodService.removeAllVideo(videoIdList);
        return R.ok();
    }

    /**
     * 根据视频ID获取视频播放凭证
     *
     * @param id
     * @return
     */
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable("id") String id) {
        try {

            // 1.创建初始化对象
            DefaultAcsClient client = InitObject.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);

            // 2.创建获取视频凭证request和response
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

            // 3.向request对象里面设置视频id
            request.setVideoId(id);

            // 4.调用初始化对象中的方法，传递request，获取数据
            response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth", playAuth);
        } catch (Exception e) {
            throw new GuliException(20001, "获取视频凭证失败！");
        }
    }
}
