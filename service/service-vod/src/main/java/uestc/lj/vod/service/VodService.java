package uestc.lj.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    String uploadVideo(MultipartFile file);

    void removeAllVideo(List<String> videoIdList);
}
