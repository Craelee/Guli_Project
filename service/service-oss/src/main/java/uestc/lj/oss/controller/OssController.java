package uestc.lj.oss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import uestc.lj.commonutils.R;
import uestc.lj.oss.service.OssService;

@RestController
@RequestMapping("/eduOss/fileoss")

public class OssController {

    @Autowired
    private OssService ossService;

    /**
     * 上传头像的方法
     *
     * @return 返回上传到oss的路径
     */
    @PostMapping
    public R uploadOssFile(MultipartFile file) {
        String url = ossService.uploadFileAvatar(file);
        return R.ok().data("url", url);
    }
}
