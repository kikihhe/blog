package com.xiaohe.common;

import com.aliyun.oss.OSSClient;

import com.xiaohe.config.AliyunConfig;
import com.xiaohe.constants.Constants;
import com.xiaohe.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * 上传文件接口
 */
@Slf4j
@RestController
public class CommonController {
    @Autowired
    private OSSClient ossClient;

    @Autowired
    private AliyunConfig aliyunConfig;


    @PostMapping("/upload")
    public Result upload(MultipartFile img) {

        // 获取原始文件名
        String fileName = img.getOriginalFilename(); // abc.jpg
        // 获取后缀
        String suffix = fileName.substring(fileName.lastIndexOf(".")); // .jpg

        // 生成新的路径
        String s =  UUID.randomUUID() + suffix; // 15823941994-asdfewr2345wfed2312.jpg

        // 最终的文件名为
        String finalName = Constants.User.ALIYUN_OSS + s;


        // 上传至oss
        // 3. 上传至阿里OSS
        try {
            ossClient.putObject(aliyunConfig.getBucketName(), s, new ByteArrayInputStream(img.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException("上传失败!!");
        }

        return Result.success(finalName);
    }
}