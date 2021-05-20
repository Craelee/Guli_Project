package uestc.lj.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uestc.lj.servicebase.exception.GuliException;
import uestc.lj.vod.service.VodService;
import uestc.lj.vod.utils.ConstantVodUtils;
import uestc.lj.vod.utils.InitObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {

    @Override
    public String uploadVideo(MultipartFile file) {
        try {
            //上传文件原始名称
            String fileName = file.getOriginalFilename();
            //上传之后显示名称
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            //上传文件输入流
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET, title, fileName, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            if (response.isSuccess()) {
                return response.getVideoId();
            } else {
                throw new GuliException(20001, "视频上传失败！");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void removeAllVideo(List<String> videoIdList) {
        try {
            // 1.初始化对象
            DefaultAcsClient client = InitObject.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            // 2.创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            String videoIds = StringUtils.join(videoIdList.toArray(), ",");
            // 3.向request设置视频id
            request.setVideoIds(videoIds);
            // 4.调用初始化对象的方法实现删除
            client.getAcsResponse(request);
        } catch (
                Exception e) {
            e.printStackTrace();
            throw new GuliException(20001, "无法删除小节中的视频！");
        }
    }
}
