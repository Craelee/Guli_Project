package uestc.lj.vod;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

public class TestVod {
    public static void main(String[] args) throws ClientException {

    }

    /**
     * 根据视频id获取视频的播放地址
     *
     * @throws ClientException
     */
    public static void getPlayUrl() throws ClientException {
        // 1.根据视频ID获取视频播放地址
        // 创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI5tDF7Ufo9ihz5BSs8RCh", "P1XeMOfwrrFDVDTncJYiXBAmKMlqlo");

        // 2.创建获取视频地址request和response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        // 3.向request对象里面设置视频id
        request.setVideoId("077bcfd11e9d49bd96d00ba4ca076c1d");

        // 4.调用初始化对象中的方法，传递request，获取数据
        response = client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }

    /**
     * 根据视频id获取视频的播放凭证
     *
     * @throws ClientException
     */
    public static void getPlayAuth() throws ClientException {
        // 1.根据视频ID获取视频播放地址
        // 创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI5tDF7Ufo9ihz5BSs8RCh", "P1XeMOfwrrFDVDTncJYiXBAmKMlqlo");

        // 2.创建获取视频凭证request和response
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        // 3.向request对象里面设置视频id
        request.setVideoId("077bcfd11e9d49bd96d00ba4ca076c1d");

        // 4.调用初始化对象中的方法，传递request，获取数据
        response = client.getAcsResponse(request);
        //播放凭证
        System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
    }

    /**
     * 上传本地视频
     */
    public static void uploadVideo(){
        String accessKeyId = "LTAI5tDF7Ufo9ihz5BSs8RCh";
        String accessKeySecret = "P1XeMOfwrrFDVDTncJYiXBAmKMlqlo";
        String title = "video2-副本";
        String fileName = "D:\\谷粒前端\\素材\\video2.mp4";
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        request.setPartSize(2 * 1024 * 1024L);
        request.setTaskNum(1);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        System.out.println("RequestId=" + response.getRequestId() + "\n");
        if (response.isSuccess()) {
            System.out.println("VideoId=" + response.getVideoId() + "\n");
        } else {
            System.out.println("VideoId=" + response.getVideoId() + "\n");
            System.out.println("ErrorCode=" + response.getCode() + "\n");
            System.out.println("ErrorMessage=" + response.getMessage() + "\n");
        }
    }
}
